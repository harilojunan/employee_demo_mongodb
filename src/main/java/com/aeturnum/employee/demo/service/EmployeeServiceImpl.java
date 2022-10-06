package com.aeturnum.employee.demo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aeturnum.employee.demo.entity.Employee;
import com.aeturnum.employee.demo.exceptions.ResourceAlreadyFoundException;
import com.aeturnum.employee.demo.exceptions.ResourceNotFoundException;
import com.aeturnum.employee.demo.repository.EmployeeRepo;
import com.aeturnum.employee.demo.util.AppConstants;

@Service
@CacheConfig(cacheNames = "employee")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

//	@Autowired
//	private ModelMapper modelMapper;

	public EmployeeServiceImpl(EmployeeRepo employeeRepo, ModelMapper modelMapper) {
		this.employeeRepo = employeeRepo;
//		this.modelMapper = modelMapper;
	}

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Override
	public Employee saveEmployee(Employee employee) {
		logger.info("Loggig Stated!!! Inside Employee Service @Save Employee Method Running");
		if (employeeRepo.existsById(employee.getId())) {
			throw new ResourceAlreadyFoundException("User already found");
		}
		Employee savedEmployee = employeeRepo.save(employee);
		return savedEmployee;
	}

	@Override
	@Cacheable(value = AppConstants.HASH_VALUE)
	public List<Employee> getAllEmployees() {
		logger.info("Loggig Stated!!! Inside Employee Service @Get All Employee Method Running");
		return employeeRepo.findAll();
	}

	@Override
	@Cacheable(value = AppConstants.HASH_VALUE, key = AppConstants.HASH_KEY)
	public Employee getEmployeeById(String id) {
		logger.info("Loggig Stated!!! Inside Employee Service @Get Employee Method Running");
		Employee employee;
		if (employeeRepo.findById(id).isEmpty()) {
			throw new ResourceNotFoundException("User not found with id :" + id);
		} else {
			employee = employeeRepo.findById(id).get();
		}
		return employee;
	}

	// Need to check
	@Override
	@CachePut(key = "#employee.id")
	public Employee updateEmployee(Employee employeeRequest, String id) {
		logger.info("Loggig Stated!!! Inside Employee Service @Find By ID Employee Method Running");

		if (employeeRepo.findById(id).isEmpty()) {
			throw new ResourceNotFoundException("User not found with id :" + id);
		} else {
			Employee employee = employeeRepo.findById(id).get();
			logger.info("Loggig Stated!!! Inside Employee Service @Update Employee Method Running");
			employee.setId(employeeRequest.getId());
			return employeeRepo.save(employeeRequest);
		}
	}

	@Override
	@CacheEvict(value = AppConstants.HASH_VALUE, key = AppConstants.HASH_KEY)
	public String deleteEmployee(String id) {
		logger.info("Loggig Stated!!! Inside Employee Service @Find By ID Employee Method Started");
		if (employeeRepo.findById(id).isEmpty()) {
			throw new ResourceNotFoundException("User not found with id :" + id);
		} else {
			logger.info("Loggig Stated!!! Inside Employee Service @Delete Employee Method Started");
			employeeRepo.deleteById(id);
		}
		return "Employee Deleted Successfully with id : "+id;
	}

//	private EmployeeDto mapToDto(Employee employee) {
//		EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
//		return employeeDto;
//	}
//	
//	private Employee mapToEntity(EmployeeDto employeeDto) {
//		Employee employee = modelMapper.map(employeeDto, Employee.class);
//		return employee;
//	}

}
