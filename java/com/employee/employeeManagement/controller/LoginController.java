package com.employee.employeeManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@Autowired
	public EmployeeService empService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
	public String adminLogin(@RequestParam String empId, @RequestParam String password, Model model) {

		try {
			boolean res = empService.isLoginSuccessful(Integer.parseInt(empId), password);
			if (res) {
				model.addAttribute("id", empId);

				Employee emp = empService.findEmployeeById(Integer.parseInt(empId));
				int flag = 0;

				for (Role role : emp.getRoles()) {
					if (role.getRoleName().equalsIgnoreCase("ADMIN")) {
						flag = 1;
						break;
					}
				}

				if (flag == 1)
					return "admin";

				else {
					model.addAttribute("employee", emp);
					return "employeeDetails";
				}

			}
		} catch (EmployeeNotFoundException e) {
			model.addAttribute("message", "Bad credentials");
		}
		return "login";
	}

	@RequestMapping(value = "/adminlogout", method = RequestMethod.GET)
	public String logout(Model model, SessionStatus status) {
		status.setComplete();
		return "login";
	}

}
