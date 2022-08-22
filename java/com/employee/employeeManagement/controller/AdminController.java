package com.employee.employeeManagement.controller;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.employee.employeeManagement.entity.ApplyLeave;
import com.employee.employeeManagement.entity.Employee;
import com.employee.employeeManagement.entity.LeaveInfoEmployees;
import com.employee.employeeManagement.entity.LeaveInformation;
import com.employee.employeeManagement.entity.Role;
import com.employee.employeeManagement.exception.EmployeeNotFoundException;
import com.employee.employeeManagement.service.ApplyLeaveService;
import com.employee.employeeManagement.service.EmployeeService;
import com.employee.employeeManagement.service.LeaveEmployeeService;
import com.employee.employeeManagement.service.LeaveService;
@SessionAttributes("id")
@Controller
public class AdminController {

	@Autowired
	private EmployeeService empService;

	@Autowired
	private LeaveEmployeeService lEmpService;

	@Autowired
	private LeaveService lservice;

	@Autowired
	private ApplyLeaveService alService;

	// get list of employees
	@GetMapping("/employees")
	public String listEmployee(Model model, HttpSession session) {

		if (session.getAttribute("id") != null) {
			List<Employee> employees = empService.getAllEmployee();
			model.addAttribute("employees", employees);
			return "employeelist";
		} else
			return "login";

	}

	// add new employee form
	@GetMapping("/employees/new")
	public String newEmployee(Model model,HttpSession session) {
 
		if (session.getAttribute("id") != null) {
		List<Role> li = empService.getRoles();

		Employee emp = new Employee();
		model.addAttribute("employee", emp);
		model.addAttribute("listRoles", li);
		model.addAttribute("title", "Add new Employee");
		return "employeeform";
		} else
			return "login";

	}

	// save a new employee
	@PostMapping("/employees/save")
	public String saveEmployee(Employee employee, RedirectAttributes redirectAtt,HttpSession session) throws EmployeeNotFoundException {
		if (session.getAttribute("id") != null) {
		  empService.saveEmployee(employee);
		  redirectAtt.addFlashAttribute("message", "Employee has been saved succesfully");
		  return "redirect:/employees";
		}
		else
			return "login";
	}

	// accept leave details for all employees
	@GetMapping("/employees/Setleave")
	public String enterLeaveDetails(Model model,HttpSession session) {
		if (session.getAttribute("id") != null) {
		model.addAttribute("leave", new LeaveInformation());
		return "enterLeaveDetails";
		}
		else
			return "login";
	}

	//save leave details in Leave_Information table
	@PostMapping("/employees/leave/save")
	public String saveLeaveInformation(Model model,LeaveInformation obj,HttpSession session) {

		if (session.getAttribute("id") != null) {
		   lservice.saveLeaveDetails(obj);
		   model.addAttribute("leave", obj);
		   return "leaveDetails";	
		}
		else
			return "login";
	}

   //for employee= view leave details to apply for leave	
	@GetMapping("/employees/leave/view/{empId}")
	public String viewLeaveDetails(Model model, @PathVariable(name = "empId") int empId,HttpSession session) throws EmployeeNotFoundException,java.sql.SQLSyntaxErrorException {
		if (session.getAttribute("id") != null) {
			try {
			      if(lservice.getLeaveDetails().equals(null)==false) {
						Employee em=empService.findEmployeeById(empId);
				        LeaveInfoEmployees obj = lEmpService.getEmployeeLeaveDetails(empId);
						model.addAttribute("leave", obj);
				        model.addAttribute("employee", em);
                        return "EmployeeleaveDetails";
				 }
			}
			catch(Exception e) {
				Employee em=empService.findEmployeeById(empId);
				model.addAttribute("employee",em);
				model.addAttribute("message","Leave Details are not yet set");
				return "employeeDetails";
			}
			Employee em=empService.findEmployeeById(empId);
			model.addAttribute("employee",em);
			model.addAttribute("message","Leave Details are not yet set");
			return "employeeDetails";
		}//close if
		else
		return "login";
		
	}
	
	//set those leaves for all the employees
	@GetMapping("/leaveDetails/save")
	public String saveLeaveForEmployees(RedirectAttributes redirectAtt,HttpSession session,Model model) {

		if (session.getAttribute("id") != null) {
		 
			LeaveInformation obj = lservice.getLeaveDetails();
     		List<Employee> listEmp = empService.getAllEmployee();
	     	for (Employee emp : listEmp) {
       			LeaveInfoEmployees leaveForEmp = new LeaveInfoEmployees();
      			leaveForEmp.setEmployeeId(emp.getEmployeeId());
		    	leaveForEmp.setCovidLeave(obj.getCovidLeave());
			    leaveForEmp.setEarnedLeave(obj.getEarnedLeave());
		    	leaveForEmp.setInicidentalLeave(obj.getInicidentalLeave());
			    leaveForEmp.setLeaveWithoutPay(obj.getLeaveWithoutPay());
			    leaveForEmp.setShortLeave(obj.getShortLeave());
       			lEmpService.saveLeaveInfo(leaveForEmp);
		    
	     	}
            model.addAttribute("message", "Leave For Employees set successfully");
            List<Employee> employees = empService.getAllEmployee();
			model.addAttribute("employees", employees);
			return "employeelist";
		   }
		else
			return "login";
	}

	//view leave details for admin	
	@GetMapping("/leaveDetails/view")
	public String getLeaveDetails(Model model,HttpSession session) {

		
		if (session.getAttribute("id") != null) {
			 try {
			     if(lservice.getLeaveDetails().equals(null)==false){
			    	 System.out.print(lservice.getLeaveDetails());
			    	 model.addAttribute("leave", lservice.getLeaveDetails());
			         return "leaveDetails";
			     }
			}
			catch(Exception e) {   
				 model.addAttribute("message","You have not yet set the leave details");
				 List<Employee> employees = empService.getAllEmployee();
					model.addAttribute("employees", employees);
					return "employeelist";
			    
			 }
			 model.addAttribute("message","You have not yet set the leave details");
			 List<Employee> employees = empService.getAllEmployee();
				model.addAttribute("employees", employees);
				return "employeelist";
			
		}//close if for session
		else
			return "login";
	}

	//employee can check the status of all the leaves that he applied for
	@GetMapping("/employees/leave/leaveStatus/{employeeId}")
	public String checkStatus(Model model, @PathVariable(name = "employeeId") int empid, RedirectAttributes att,HttpSession session) throws EmployeeNotFoundException {
        
		if (session.getAttribute("id") != null) {
		    if(alService.findLeaveForEmployeeId(empid)!=null) {
		       List<ApplyLeave> obj = alService.findLeaveForEmployeeId(empid);
		        model.addAttribute("applyLeaves", obj);
		        return "checkLeaveStatus";
		     }
		     else {
			    Employee e=empService.findEmployeeById(empid);
			    model.addAttribute("message","You have not applied for leave yet!");
		      	model.addAttribute("employee",e);
			    return "employeeDetails";
		     }
		}
		else
			return "login";
		
	}

	//employee apply leave form getting saved and diffrenet validations are being checked
	@PostMapping("/employees/apply/save")
	public String saveEmpLeave(Model model, ApplyLeave obj, RedirectAttributes att,HttpSession session) throws EmployeeNotFoundException {

		if (session.getAttribute("id") != null) {
		   int i = obj.getEmployeeId();
		   Employee e=empService.findEmployeeById(i);
		   LeaveInfoEmployees lobj = lEmpService.getEmployeeLeaveDetails(i);
		   ChronoLocalDate currentDate= LocalDate.from(ZonedDateTime.now());
		   String lDate=obj.getFromDate();
		   LocalDate leaveDate=null;
		   try {
		    leaveDate = LocalDate.parse(lDate);
		   }
		   catch(Exception exc) {
			   model.addAttribute("message","You did not enter the leave in correct format!Please try again");
			   model.addAttribute("employee", e);
			   return "employeeDetails";
		   }
		   if(leaveDate.isAfter(currentDate)||leaveDate.equals(currentDate)) {
		       
			   String type = obj.getLeaveCategory();
	    	   int ct = 0;
	   	       switch (type) {
    		    case "Earned Leave":
			    if (lobj.getEarnedLeave() >= obj.getNoOfDays()) {
				    System.out.print("Earned leave!");
				    obj.setLeaveStatus("PENDING");
			   	   	  alService.saveEmployeeLeave(obj);
				    ct++;
			    }
			    break;
	    	
    		    case "Covid Leave":
			    if (lobj.getCovidLeave() >= obj.getNoOfDays()) {
			    	obj.setLeaveStatus("PENDING");
			   	   	  alService.saveEmployeeLeave(obj);
				    System.out.print("covid leave!");
				    ct++;
			     }
			    break;
		    
    		    case "Incidental Leave":
			    if(lobj.getInicidentalLeave() >= obj.getNoOfDays()) {
			    	obj.setLeaveStatus("PENDING");
			   	   	  alService.saveEmployeeLeave(obj);
				   System.out.print("IL leave!");
			  	   ct++;
		    	}
		    	break;
		    
    		    case "Leave Without Pay":
			     if(lobj.getLeaveWithoutPay() >= obj.getNoOfDays()) {
			    	 obj.setLeaveStatus("PENDING");
			   	   	  alService.saveEmployeeLeave(obj);
				    System.out.print("LWP leave!");
				     ct++;
			     }
			     break;
		    
    		    case "Short Leave":
			    if (lobj.getShortLeave() >= obj.getNoOfDays()) {
			    	obj.setLeaveStatus("PENDING");
			   	   	  alService.saveEmployeeLeave(obj);
				   System.out.print("short leave!");
				   ct++;
			    }
			    break;
		   }//close switch case
		     if(ct>0) {
		    	 model.addAttribute("message","You have succesfully applied for leave!");
				   model.addAttribute("employee", e);
				   return "employeeDetails";
		     }
	   	      
		     else if (ct == 0) {
			     model.addAttribute("message", "You don't have sufficient leave in this category");
        	     model.addAttribute("employee", e);
		         return "employeeDetails";
		     }
		       model.addAttribute("message","You did not enter the leave from date currectly!Please try again");
			   model.addAttribute("employee", e);
			   return "employeeDetails";
			  
		   }//close if
		   model.addAttribute("message","You did not enter the leave from date currectly!Please try again");
		   model.addAttribute("employee", e);
		   return "employeeDetails";
		  
		}//close if for session
		else
		return "login";
		
	}

	//admin can view pending leaves of employees
	@GetMapping("/employees/leave/allleaves")
	public String viewLeave(Model model,HttpSession session) {

		if (session.getAttribute("id") != null) {
		   List<ApplyLeave> obj = alService.getAllPendingLeave();
		   model.addAttribute("pendingLeaves", obj);
	    	return "viewPendingLeaves";
		}
		else
			return "login";
	}
	

	//admin can approve pending leave and then that leave will be decremented from table storing leaves for employees
	@GetMapping("/employees/leave/allleaves/updatepending/{leaveid}")
	public String updatePendingLeave(@PathVariable(name = "leaveid") int leaveid, RedirectAttributes att,HttpSession session) {

	if (session.getAttribute("id") != null) {
		ApplyLeave obj = alService.getLeaveByLeaveId(leaveid);
		int i = obj.getEmployeeId();
		String typ = obj.getLeaveCategory();

		LeaveInfoEmployees lobj = lEmpService.getEmployeeLeaveDetails(i);
		obj.setLeaveStatus("APPROVED");
		switch (typ) {
		case "Earned Leave":
			lobj.setEarnedLeave(lobj.getEarnedLeave() - obj.getNoOfDays());
			break;
		case "CovidLeave":
			lobj.setCovidLeave(lobj.getCovidLeave() - obj.getNoOfDays());
			break;
		case "Incidental Leave":
			lobj.setInicidentalLeave(lobj.getInicidentalLeave() - obj.getNoOfDays());
			break;
		case "Leave Without Pay":
			lobj.setLeaveWithoutPay(lobj.getLeaveWithoutPay() - obj.getNoOfDays());
			break;
		case "Short Leave":
			lobj.setShortLeave(lobj.getShortLeave() - obj.getNoOfDays());
			break;
		}

		lEmpService.saveLeaveInfo(lobj);
		alService.saveEmployeeLeave(obj);
		att.addAttribute("message", "Leave Approved");
		return "redirect:/employees/leave/allleaves";
	}//close if for session
		else
			return "login";
	}
}
