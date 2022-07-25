package com.employee.employeeManagement.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.employee.employeeManagement.entity.Employee;

@Repository
@Transactional
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {

}
