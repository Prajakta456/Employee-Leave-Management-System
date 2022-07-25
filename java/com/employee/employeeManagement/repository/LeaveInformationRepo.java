package com.employee.employeeManagement.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.employee.employeeManagement.entity.LeaveInformation;

@Repository
public interface LeaveInformationRepo extends CrudRepository<LeaveInformation,Integer>{

}
