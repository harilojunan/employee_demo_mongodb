package com.aeturnum.employee.demo.service;

import java.util.List;

import com.aeturnum.employee.demo.entity.Employee;
import com.aeturnum.employee.demo.exceptions.ResourceAlreadyFoundException;
import com.aeturnum.employee.demo.exceptions.ResourceNotFoundException;

/**
 * The Interface EmployeeService.
 */
public interface EmployeeService {
	
	/**
     * Post employee.
     *
     * @return the employee
     */
	public Employee saveEmployee(Employee employee) throws ResourceAlreadyFoundException;
	
	/**
     * Edit employee.
     *
     * @return the employee
     */
	public Employee updateEmployee(Employee employeeRequest, String id);
	
	/**
     * Retrieve employees.
     *
     * @return List of employees
     */
	public List<Employee> getAllEmployees() throws ResourceNotFoundException;
	
	/**
     * Retrieve employee.
     *
     * @return the employee object
     */
	public Employee getEmployeeById(String id) throws ResourceNotFoundException;
	
	/**
     * Delete employee.
     *
     * @return void
     */
	public String deleteEmployee(String id);

}
