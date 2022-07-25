package com.employee.employeeManagement.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employee.employeeManagement.entity.ApplyLeave;
import com.employee.employeeManagement.repository.ApplyLeaveRepo;
@Service
public class ApplyLeaveService {

	@Autowired
	private ApplyLeaveRepo repo;
		
	public List<ApplyLeave> getAllEmployeeLeave(){
		
		return (List<ApplyLeave>)repo.findAll();
	}
	
    public List<ApplyLeave> findLeaveForEmployeeId(int id) {
		List<ApplyLeave> li=new ArrayList<ApplyLeave>();
    	
		for(ApplyLeave obj:getAllEmployeeLeave()) {
			if(obj.getEmployeeId()==id) {
				li.add(obj);
			}
		}
		
		return li;
	}
	
	public ApplyLeave saveEmployeeLeave(ApplyLeave obj) {
		ApplyLeave emp=repo.save(obj);
		return emp;		
	}

	
	public List<ApplyLeave> getAllPendingLeave(){
		List<ApplyLeave> li=new ArrayList<>();
		for(ApplyLeave obj:getAllEmployeeLeave()) {
			if(obj.getLeaveStatus().equalsIgnoreCase("PENDING"))
				li.add(obj);
		}
	    return li;
	}
	
	
	public ApplyLeave getLeaveByLeaveId(int id) {
		return repo.findById(id).get();
	}
	
}
