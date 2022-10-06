package com.aeturnum.employee_demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.aeturnum.employee.demo.entity.Employee;
import com.aeturnum.employee.demo.exceptions.ResourceAlreadyFoundException;
import com.aeturnum.employee.demo.repository.EmployeeRepo;
import com.aeturnum.employee.demo.service.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
	
	@Mock
	private EmployeeRepo employeeRepo;
	
	@Autowired
    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;
    private Employee employee1;
    private Employee employee2;
    List<Employee> employees;
	
    @BeforeEach
    public void setUp() {
        employees = new ArrayList<>();
        employee1 = new Employee("001", "Natasha", "Romanoff", new BigDecimal("210000.00"), "2002-09-30");
        employee2 = new Employee("002", "Tony", "Stark", new BigDecimal("158000.00"), "1983-07-22");
        employees.add(employee1);
        employees.add(employee2);
    }

    @AfterEach
    public void tearDown() {
        employee1 = employee2 = null;
        employees = null;
    }

    @Test
    void givenEmployeeToAddShouldReturnAddedEmployee() throws ResourceAlreadyFoundException {

        //stubbing
        when(employeeRepo.save(any())).thenReturn(employee1);
        employeeServiceImpl.saveEmployee(employee1);
       verify(employeeRepo,times(1)).save(any());

    }

    @Test
    public void GivenGetAllEmployeesShouldReturnListOfAllEmployees(){
    employeeRepo.save(employee1);
        //stubbing mock to return specific data
        when(employeeRepo.findAll()).thenReturn(employees);
        List<Employee> employeeList =employeeServiceImpl.getAllEmployees();
        assertEquals(employeeList,employees);
        verify(employeeRepo,times(1)).save(employee1);
        verify(employeeRepo,times(1)).findAll();
    }

    @Test
    public void givenIdThenShouldReturnEmployeeOfThatId() {

        Mockito.when(employeeRepo.findById("001")).thenReturn(Optional.ofNullable(employee1));
        assertThat(employeeServiceImpl.getEmployeeById(employee1.getId())).isEqualTo(employee1);
    }


}
