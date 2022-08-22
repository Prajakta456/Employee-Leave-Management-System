package com.employee.employeeManagement.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/")
	public String login() {
		return "login";
	}

	@PostMapping("/adminlogin")
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

				if (flag == 1) {
					List<Employee> employees = empService.getAllEmployee();
					model.addAttribute("employees", employees);
					return "employeelist";
				}
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

	/*void setComplete()
Mark the current handler's session processing as complete, allowing for cleanup of session attributes.*/
	/*public interface SessionStatus
Simple interface that can be injected into handler methods, allowing them to signal that their session 
processing is complete. The handler invoker may then follow up with appropriate cleanup, e.g. of session
attributes which have been implicitly created during this handler's processing (according to the @SessionAttributes annotation).*/
	
	
	@GetMapping("/adminlogout")
	public String logout(Model model, SessionStatus status) {
		status.setComplete();
		return "login";
	}

}
