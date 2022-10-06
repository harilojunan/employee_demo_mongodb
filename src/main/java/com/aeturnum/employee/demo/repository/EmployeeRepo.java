package com.aeturnum.employee.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aeturnum.employee.demo.entity.Employee;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, String> {

}
