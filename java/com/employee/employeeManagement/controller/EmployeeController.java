package com.employee.employeeManagement.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.employee.employeeManagement.entity.ApplyLeave;
import com.employee.employeeManagement.entity.Employee;
import com.employee.employeeManagement.entity.Role;
import com.employee.employeeManagement.exception.EmployeeNotFoundException;
import com.employee.employeeManagement.service.EmployeeService;
import com.employee.employeeManagement.service.LeaveEmployeeService;

@SessionAttributes("id")
@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveEmployeeService leaveEmployeeService;

    public EmployeeController(EmployeeService employeeService, LeaveEmployeeService leaveEmployeeService) {
        this.employeeService = employeeService;
        this.leaveEmployeeService = leaveEmployeeService;
    }

    // Admin can update employee details
    @GetMapping("/employees/update/{empId}")
    public String viewUpdateEmployeeDetails(Model model, @PathVariable(name = "empId") int empId, HttpSession session) throws EmployeeNotFoundException {

        if (session.getAttribute("id") == null) {
            return "login";
        }

        Employee employee = employeeService.findEmployeeById(empId);
        List<Role> roles = employeeService.getRoles();

        model.addAttribute("employee", employee);
        model.addAttribute("listRoles", roles);
        model.addAttribute("title", "Update employee id:" + empId);

        return "employeeform";
    }

    // Employee can view personal details
    @GetMapping("/employees/empprofile/view/{empId}")
    public String getPersonalDetails(Model model, @PathVariable(name = "empId") int empId, HttpSession session) throws EmployeeNotFoundException {

        if (session.getAttribute("id") == null) {
            return "login";
        }

        Employee employee = employeeService.findEmployeeById(empId);

        model.addAttribute("employee", employee);

        return "viewemployeeprofile";
    }

    // Employee can open leave application form
    @GetMapping("/employees/leave/applyNew/{empId}")
    public String applyForLeave(Model model, @PathVariable(name = "empId") int empId, HttpSession session) {

        if (session.getAttribute("id") == null) {
            return "login";
        }

        ApplyLeave applyLeave = new ApplyLeave();
        applyLeave.setEmployeeId(empId);

        model.addAttribute("applyLeave", applyLeave);

        return "ApplyForLeave";
    }
}

