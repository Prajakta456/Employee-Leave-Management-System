package com.employee.employeeManagement.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class ApplyLeave {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int leaveid; 
	
	private int employeeId;
	private String fromDate;
	private int noOfDays;
	private String leaveCategory;
	private String leaveStatus;
	private String LeaveReason;
	
	
	public ApplyLeave(int leaveid, int employeeId, String fromDate,int noOfDays, String leaveCategory,
			String leaveStatus, String leaveReason) {
		
		this.leaveid = leaveid;
		this.employeeId = employeeId;
		this.fromDate = fromDate;

		this.noOfDays=noOfDays;
		this.leaveCategory = leaveCategory;
		this.leaveStatus = leaveStatus;
		LeaveReason = leaveReason;
	}
	
	public ApplyLeave() {
	
	}

	public int getLeaveid() {
		return leaveid;
	}

	public void setLeaveid(int leaveid) {
		this.leaveid = leaveid;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getLeaveCategory() {
		return leaveCategory;
	}

	public void setLeaveCategory(String leaveCategory) {
		this.leaveCategory = leaveCategory;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public String getLeaveReason() {
		return LeaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		LeaveReason = leaveReason;
	}
}
