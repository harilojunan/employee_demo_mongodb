package com.aeturnum.employee_demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.aeturnum.employee.demo.controller.EmployeeController;
import com.aeturnum.employee.demo.entity.Employee;
import com.aeturnum.employee.demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
	
	//unit test
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;
    private Employee employee;
    private List<Employee> employeeList;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setup(){
        employee = new Employee("001", "Natasha", "Romanoff", new BigDecimal("1000.00"), "2002-09-30");
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @AfterEach
    void tearDown() {
        employee = null;
    }

    @Test
    public void PostMappingOfEmployee() throws Exception{
        when(employeeService.saveEmployee(any())).thenReturn(employee);
        mockMvc.perform(post("/api/v1/employees").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(employee))).
                andExpect(status().isCreated());
        verify(employeeService,times(1)).saveEmployee(any());
    }

    @Test
    public void GetMappingOfAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employeeList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees").
                contentType(MediaType.APPLICATION_JSON).
                        content(asJsonString(employee))).
                andDo(MockMvcResultHandlers.print());
        verify(employeeService).getAllEmployees();
        verify(employeeService,times(1)).getAllEmployees();
    }

    @Test
    public void GetMappingOfEmployeeShouldReturnRespectiveEmployee() throws Exception {
        when(employeeService.getEmployeeById(employee.getId())).thenReturn(employee);
        mockMvc.perform(get("/api/v1/employees/1")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void DeleteMappingUrlAndIdThenShouldReturnDeletedEmployee() throws Exception {
    	when(employeeService.deleteEmployee(employee.getId())).thenReturn("Successfully Deleted");
        mockMvc.perform(delete("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
