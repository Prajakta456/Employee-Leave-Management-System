package com.employee.employeeManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.employee.employeeManagement.entity.ApplyLeave;

public interface ApplyLeaveRepo extends CrudRepository<ApplyLeave, Integer> {

    List<ApplyLeave> findByEmployeeId(int employeeId);
}
