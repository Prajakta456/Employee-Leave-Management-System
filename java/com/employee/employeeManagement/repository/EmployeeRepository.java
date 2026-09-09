package com.employee.employeeManagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.employee.employeeManagement.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}