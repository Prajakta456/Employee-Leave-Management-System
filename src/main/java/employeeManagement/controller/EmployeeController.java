package employeeManagement.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import employeeManagement.entity.ApplyLeave;
import employeeManagement.entity.Employee;
import employeeManagement.entity.Role;
import employeeManagement.exception.EmployeeNotFoundException;
import employeeManagement.service.EmployeeService;
import employeeManagement.service.LeaveEmployeeService;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveEmployeeService leaveEmployeeService;

    public EmployeeController(EmployeeService employeeService,
                              LeaveEmployeeService leaveEmployeeService) {
        this.employeeService = employeeService;
        this.leaveEmployeeService = leaveEmployeeService;
    }

    // Admin can update employee details
    @GetMapping("/employees/update/{empId}")
    public String viewUpdateEmployeeDetails(
            Model model,
            @PathVariable(name = "empId") int empId)
            throws EmployeeNotFoundException {

        Employee employee = employeeService.findEmployeeById(empId);
        List<Role> roles = employeeService.getRoles();

        model.addAttribute("employee", employee);
        model.addAttribute("listRoles", roles);
        model.addAttribute("title", "Update employee id: " + empId);

        return "employeeform";
    }

    // Employee can view personal details
    @GetMapping("/employees/empprofile/view/{empId}")
    public String getPersonalDetails(
            Model model,
            @PathVariable(name = "empId") int empId,
            Authentication authentication)
            throws EmployeeNotFoundException {

        int loggedInEmployeeId = Integer.parseInt(authentication.getName());

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);

        // Employee can only view their own profile
        if (!isAdmin && loggedInEmployeeId != empId) {
            return "redirect:/";
        }

        Employee employee = employeeService.findEmployeeById(empId);

        model.addAttribute("employee", employee);

        return "viewemployeeprofile";
    }

    // Employee can open leave application form
    @GetMapping("/employees/leave/applyNew/{empId}")
    public String applyForLeave(
            Model model,
            @PathVariable(name = "empId") int empId,
            Authentication authentication) {

        int loggedInEmployeeId = Integer.parseInt(authentication.getName());

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);

        // Employee can apply only for their own ID
        if (!isAdmin && loggedInEmployeeId != empId) {
            return "redirect:/";
        }

        ApplyLeave applyLeave = new ApplyLeave();

        // Keep this for the existing form
        applyLeave.setEmployeeId(empId);

        model.addAttribute("applyLeave", applyLeave);

        return "ApplyForLeave";
    }
}