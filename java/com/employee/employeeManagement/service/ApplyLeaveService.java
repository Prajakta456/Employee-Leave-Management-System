package com.employee.employeeManagement.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.employee.employeeManagement.entity.ApplyLeave;
import com.employee.employeeManagement.repository.ApplyLeaveRepo;

@Service
public class ApplyLeaveService {

    private final ApplyLeaveRepo repo;

    public ApplyLeaveService(ApplyLeaveRepo repo) {
        this.repo = repo;
    }

    // Get all employee leave applications
    public List<ApplyLeave> getAllEmployeeLeave() {
        return (List<ApplyLeave>) repo.findAll();
    }

    // Get leave applications for a particular employee
    @Cacheable(value = "employeeLeaves", key = "#id")
    public List<ApplyLeave> findLeaveForEmployeeId(int id) {

        System.out.println(
                "Fetching employee leaves from DB: " + id);

        return repo.findByEmployeeId(id);
    }

    // Save or update leave application
    //
    // 1. Updates the individual leave cache
    // 2. Clears the employee's leave-list cache because
    //    the list has changed
    @Caching(
            put = @CachePut(
                    value = "applyLeaveById",
                    key = "#result.leaveId"
            ),
            evict = @CacheEvict(
                    value = "employeeLeaves",
                    key = "#result.employeeId"
            )
    )
    public ApplyLeave saveEmployeeLeave(ApplyLeave obj) {

        return repo.save(obj);
    }

    // Get all pending leave applications
    public List<ApplyLeave> getAllPendingLeave() {

        return getAllEmployeeLeave()
                .stream()
                .filter(leave ->
                        "PENDING".equalsIgnoreCase(
                                leave.getLeaveStatus()))
                .toList();
    }

    // Get leave application by leave ID
    @Cacheable(value = "applyLeaveById", key = "#id")
    public ApplyLeave getLeaveByLeaveId(int id) {

        System.out.println(
                "Fetching leave from DB: " + id);

        return repo.findById(id).orElse(null);
    }
}

