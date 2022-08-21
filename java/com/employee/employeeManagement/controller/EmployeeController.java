package com.employee.employeeManagement.controller;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@Autowired
	public EmployeeService service;

	@Autowired
	public LeaveEmployeeService leservice;

	// because of thymeleaf template engine you don't have to say login.html and can
	// directly say return "login"

	@GetMapping("/employees/update/{empId}")
	public String viewUpdateEmployeeDetails(Model model, @PathVariable(name = "empId") int empId,HttpSession session)
			throws EmployeeNotFoundException {

		if (session.getAttribute("id") != null) {
		  Employee emp = service.findEmployeeById(empId);
		  List<Role> li = service.getRoles();
		  model.addAttribute("employee", emp);
		  model.addAttribute("listRoles", li);
		  model.addAttribute("title", "Update employee id:" + empId);
		  return "employeeform";
		}
		else
			return "login";
	}

	
    //employee can view his details
	@GetMapping(value = "/employees/empprofile/view/{empId}")
	public String getPersonalDetails(Model model, @PathVariable(name = "empId") int empId,HttpSession session)
			throws EmployeeNotFoundException {
		if (session.getAttribute("id") != null) {
		   Employee emp = service.findEmployeeById(empId);
		   model.addAttribute("employee", emp);
		   return "viewemployeeprofile";
		}
		else
			return "login";
	}
    //http://localhost:8080/employees/leave/applyNew/2
	@GetMapping("/employees/leave/applyNew/{empId}")
	public String applyForLeave(Model model, @PathVariable(name = "empId") int empId,HttpSession session) {

		if (session.getAttribute("id") != null) {
		   ApplyLeave obj = new ApplyLeave();
		   obj.setEmployeeId(empId);
		   model.addAttribute("applyLeave", obj);

		   return "ApplyForLeave";
		}//close if for session
		else
			return "login";
	  }

	

}
