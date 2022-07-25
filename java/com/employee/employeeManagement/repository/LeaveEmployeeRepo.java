package com.employee.employeeManagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.employee.employeeManagement.entity.LeaveInfoEmployees;

@Repository
public interface LeaveEmployeeRepo extends CrudRepository<LeaveInfoEmployees,Integer>{

}
