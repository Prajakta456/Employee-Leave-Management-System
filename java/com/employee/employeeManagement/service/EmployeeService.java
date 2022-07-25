package com.employee.employeeManagement.service;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employee.employeeManagement.entity.Employee;
import com.employee.employeeManagement.entity.Role;
import com.employee.employeeManagement.exception.EmployeeNotFoundException;
import com.employee.employeeManagement.repository.EmployeeRepository;
import com.employee.employeeManagement.repository.RoleRepo;
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired 
	private RoleRepo roleR;
	
	public List<Employee> getAllEmployee(){
		return (List<Employee>) employeeRepo.findAll();
	}
	
	public Employee findEmployeeById(int id) throws EmployeeNotFoundException{
		try {
			return employeeRepo.findById(id).get();
		}
		catch (NoSuchElementException ex) {
			throw new EmployeeNotFoundException("Could not find any employee with ID " + id);
		}
	}
	
   public Employee saveEmployee(Employee employee) throws EmployeeNotFoundException {
			   
	   if(employee.getEmployeeId()!=0) {
		   Employee emp=findEmployeeById(employee.getEmployeeId());
		  
		  if(employee.getPassword().isEmpty()) {
			  employee.setPassword(emp.getPassword());
		  }
	   }  	   
		Employee employ=employeeRepo.save(employee);
		return employ;
	}
	
   
	
	public List<Role> getRoles() {
		return (List<Role>) roleR.findAll();
	}
	
	
	public boolean isLoginSuccessful(int id,String password) throws EmployeeNotFoundException {
		
		if(id>0) {
		Employee emp=findEmployeeById(id);
		System.out.println(emp.getFirstName());
		System.out.println(emp.getPassword());
		System.out.println(password);
		
		if(emp.getPassword().equals(password)) {
			return true;
		}
		  return false;
	 }//close if
		return false;
	}
	
}
