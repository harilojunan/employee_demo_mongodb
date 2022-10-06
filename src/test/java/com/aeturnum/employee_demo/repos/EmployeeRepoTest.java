package com.aeturnum.employee_demo.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aeturnum.employee.demo.entity.Employee;
import com.aeturnum.employee.demo.repository.EmployeeRepo;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class EmployeeRepoTest {
	
	@Autowired
    private EmployeeRepo employeeRepo;
    private Employee employee;
    
    @BeforeEach
    public void setUp() {

        employee = new Employee("001", "Natasha", "Romanoff", new BigDecimal("1000.00"), "2002-09-30");
    }

    @AfterEach
    public void tearDown() {
        employeeRepo.deleteAll();
        employee = null;
    }
    
    @Test
    public void givenEmployeeToAddShouldReturnAddedEmployee(){

        employeeRepo.save(employee);
        Employee fetchedEmployee = employeeRepo.findById(employee.getId()).get();
        assertEquals("1", fetchedEmployee.getId());
    }
    
    @Test
    public void GivenGetAllEmployeesShouldReturnListOfAllEmployees(){
        Employee employee1 = new Employee("001", "Natasha", "Romanoff", new BigDecimal("210000.00"), "2002-09-30");
        Employee employee2 = new Employee("002", "Tony", "Stark", new BigDecimal("158000.00"), "1983-07-22");
        employeeRepo.save(employee1);
        employeeRepo.save(employee2);

        List<Employee> employees = (List<Employee>) employeeRepo.findAll();
        assertEquals("bat", employees.get(1).getFirstName());
        

    }

    @Test
    public void givenIdThenShouldReturnEmployeeOfThatId() {
        Employee employee1 = new Employee("001", "Natasha", "Romanoff", new BigDecimal("210000.00"), "1987/05/30");
        Employee savedEmployee = employeeRepo.save(employee1);
        
        Optional<Employee> employee =  employeeRepo.findById(savedEmployee.getId());
        assertEquals(savedEmployee.getId(), employee.get().getId());
        assertEquals(savedEmployee.getFirstName(), employee.get().getFirstName());
    }

    @Test
    public void givenIdTODeleteThenShouldDeleteTheEmployee() {
        Employee employee = new Employee("003", "Mitchell", "Stark", new BigDecimal("680000"), "1984/08/16");
        employeeRepo.save(employee);
        employeeRepo.deleteById(employee.getId());
        Optional<Employee> getEmployee = employeeRepo.findById(employee.getId());
        assertEquals(Optional.empty(), getEmployee);
    }


}
