package com.employee.employeeManagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.employee.employeeManagement.entity.ApplyLeave;

@Repository
public interface ApplyLeaveRepo extends CrudRepository<ApplyLeave,Integer>{

}
