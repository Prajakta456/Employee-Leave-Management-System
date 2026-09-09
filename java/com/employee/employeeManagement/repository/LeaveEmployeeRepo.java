package com.employee.employeeManagement.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.employee.employeeManagement.entity.LeaveInfoEmployees;

public interface LeaveEmployeeRepo
        extends CrudRepository<LeaveInfoEmployees, Integer> {

    Optional<LeaveInfoEmployees> findByEmployeeId(int employeeId);
}