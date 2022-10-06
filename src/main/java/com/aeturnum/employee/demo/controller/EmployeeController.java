package com.aeturnum.employee.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aeturnum.employee.demo.entity.Employee;
import com.aeturnum.employee.demo.entity.EmployeeDto;
import com.aeturnum.employee.demo.exceptions.ResourceAlreadyFoundException;
import com.aeturnum.employee.demo.exceptions.ResourceNotFoundException;
import com.aeturnum.employee.demo.service.EmployeeService;
import com.aeturnum.employee.demo.util.AppConstants;

/**
 * The Class EmployeeController.
 */
@RestController
@RequestMapping(AppConstants.BASE_URL)
@CrossOrigin(origins = AppConstants.CROSS_ORIGIN_URL)
public class EmployeeController {

	/** The employee service. */
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ModelMapper modelMapper;

	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	/**
	 * add employee.
	 *
	 * @param the employee object
	 * @return HttpStatus OK
	 */
	@PostMapping(AppConstants.EMPLOYEES_URL)
	public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto employeeDto) throws ResourceAlreadyFoundException {
		logger.info("Loggig Stated!!! Inside Employee Controller @Save Employee Method Initiated");
		// convert DTO to entity
		Employee employeeRequest = this.modelMapper.map(employeeDto, Employee.class);

		Employee employee = employeeService.saveEmployee(employeeRequest);

		// convert entity to DTO
		EmployeeDto employeeResponse = modelMapper.map(employee, EmployeeDto.class);

		if (null != employeeResponse) {
			return new ResponseEntity<EmployeeDto>(employeeResponse, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Retrieve employees.
	 *
	 * 
	 * @return HttpStatus OK and the employees list
	 */
	@GetMapping(AppConstants.EMPLOYEES_URL)
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() throws ResourceNotFoundException {
		logger.info("Loggig Stated!!! Inside Employee Controller @Get All Employee Method Initiated");
		return new ResponseEntity<>(employeeService.getAllEmployees().stream()
				.map(employee -> modelMapper.map(employee, EmployeeDto.class)).collect(Collectors.toList()),
				HttpStatus.OK);
	}

	/**
	 * Retrieve employee.
	 *
	 * @param String id, the employee id request
	 * @return response the employee object
	 */;
	@GetMapping(AppConstants.EMPLOYEE_BY_ID_URL)
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable String id) throws ResourceNotFoundException {
		logger.info("Loggig Stated!!! Inside Employee Controller logger.info @Get Employee By ID Employee Method Initiated");
		Employee empRetrieved = employeeService.getEmployeeById(id);
		EmployeeDto employeeResponse = modelMapper.map(empRetrieved, EmployeeDto.class);
		return new ResponseEntity<EmployeeDto>(employeeResponse, HttpStatus.OK);
	}

	/**
	 * Edit employee.
	 *
	 * @param String id, the employee id request
	 * @return HttpStatus OK
	 */
	@PutMapping(AppConstants.EMPLOYEE_BY_ID_URL)
	public ResponseEntity<?> updateEmployee(@PathVariable String id, @RequestBody EmployeeDto employeeDto) {
		logger.info("Loggig Stated!!! Inside Employee Controller @Get Employee By ID Employee Method Initiated");
		Employee employeeData = employeeService.getEmployeeById(id);	
		if (employeeData.getId() != null) {
			Employee employeeRequest = modelMapper.map(employeeDto, Employee.class);
			logger.info("Loggig Stated!!! Inside Employee Controller @Update Employee Method Initiated");
			Employee employee = employeeService.updateEmployee(employeeRequest, id);
			// convert entity to DTO
			EmployeeDto employeeResponse = modelMapper.map(employee, EmployeeDto.class);
			return ResponseEntity.ok().body(employeeResponse);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Delete employee.
	 *
	 * @param String id, the employee id request
	 * @return void
	 */
	@DeleteMapping(AppConstants.EMPLOYEE_BY_ID_URL)
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") String id) {
		logger.info("Loggig Stated!!! Inside Employee Controller @Delete Employee Method Initiated");
		employeeService.deleteEmployee(id);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

}
