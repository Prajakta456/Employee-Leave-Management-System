package com.employee.employeeManagement.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.employee.employeeManagement.entity.Employee;
import com.employee.employeeManagement.entity.Role;
import com.employee.employeeManagement.exception.EmployeeNotFoundException;
import com.employee.employeeManagement.service.EmployeeService;

@SessionAttributes("id")
@Controller
public class LoginController {

    private final EmployeeService employeeService;

    public LoginController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/adminlogin")
    public String adminLogin(@RequestParam String empId, @RequestParam String password, Model model) {

        try {

            int employeeId = Integer.parseInt(empId);

            boolean loginSuccessful = employeeService.isLoginSuccessful(employeeId, password);

            if (!loginSuccessful) {
                model.addAttribute("message", "Bad credentials");
                return "login";
            }

            // Store employee ID in session
            model.addAttribute("id", empId);

            Employee employee = employeeService.findEmployeeById(employeeId);

            boolean isAdmin = false;

            for (Role role : employee.getRoles()) {

                if ("ADMIN".equalsIgnoreCase(role.getRoleName())) {
                    isAdmin = true;
                    break;
                }
            }

            if (isAdmin) {

                List<Employee> employees = employeeService.getAllEmployee();

                model.addAttribute("employees", employees);

                return "employeelist";
            }

            model.addAttribute("employee", employee);

            return "employeeDetails";

        } catch (NumberFormatException e) {

            model.addAttribute("message", "Invalid employee ID");
            return "login";

        } catch (EmployeeNotFoundException e) {

            model.addAttribute("message", "Bad credentials");
            return "login";
        }
    }

    @GetMapping("/adminlogout")
    public String logout(SessionStatus status) {

        status.setComplete();

        return "login";
    }
}
