package com.employee.employeeManagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.employee.employeeManagement.entity.LeaveInformation;

public interface LeaveInformationRepo
        extends CrudRepository<LeaveInformation, Integer> {

}