package com.employee.employeeManagement.service;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.employee.employeeManagement.entity.LeaveInfoEmployees;
import com.employee.employeeManagement.repository.LeaveEmployeeRepo;

@Service
public class LeaveEmployeeService {

    private final LeaveEmployeeRepo repoEmpL;

    public LeaveEmployeeService(LeaveEmployeeRepo repoEmpL) {
        this.repoEmpL = repoEmpL;
    }

    // Save or update employee leave information
    @CachePut(
            value = "employeeLeave",
            key = "#result.employeeId"
    )
    public LeaveInfoEmployees saveLeaveInfo(
            LeaveInfoEmployees obj) {

        return repoEmpL.save(obj);
    }

    // Get all employee leave information
    public List<LeaveInfoEmployees> getAllLeaves() {

        return (List<LeaveInfoEmployees>) repoEmpL.findAll();
    }

    // Get leave information for a particular employee
    @Cacheable(
            value = "employeeLeave",
            key = "#empId"
    )
    public LeaveInfoEmployees getEmployeeLeaveDetails(
            int empId) {

        System.out.println(
                "Fetching employee leave details from DB: "
                        + empId);

        return repoEmpL.findByEmployeeId(empId)
                .orElse(null);
    }
}

