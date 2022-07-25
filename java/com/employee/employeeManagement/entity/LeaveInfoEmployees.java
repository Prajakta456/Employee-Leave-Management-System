package com.employee.employeeManagement.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LeaveInfoEmployees {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaveId;
	
	private int employeeId;
	
	private int earnedLeave;
	
	private int covidLeave;
	
	private int inicidentalLeave;	
	
	private int leaveWithoutPay;
	  
	private int shortLeave;
	
	public LeaveInfoEmployees() {
		
	}

	public LeaveInfoEmployees(int employeeId, int earnedLeave, int covidLeave, int inicidentalLeave,
			int leaveWithoutPay, int shortLeave) {
		this.employeeId = employeeId;
		this.earnedLeave = earnedLeave;
		this.covidLeave = covidLeave;
		this.inicidentalLeave = inicidentalLeave;
		this.leaveWithoutPay = leaveWithoutPay;
		this.shortLeave = shortLeave;
	
	}

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getEarnedLeave() {
		return earnedLeave;
	}

	public void setEarnedLeave(int earnedLeave) {
		this.earnedLeave = earnedLeave;
	}

	public int getCovidLeave() {
		return covidLeave;
	}

	public void setCovidLeave(int covidLeave) {
		this.covidLeave = covidLeave;
	}

	public int getInicidentalLeave() {
		return inicidentalLeave;
	}

	public void setInicidentalLeave(int inicidentalLeave) {
		this.inicidentalLeave = inicidentalLeave;
	}

	public int getLeaveWithoutPay() {
		return leaveWithoutPay;
	}

	public void setLeaveWithoutPay(int leaveWithoutPay) {
		this.leaveWithoutPay = leaveWithoutPay;
	}

	public int getShortLeave() {
		return shortLeave;
	}

	public void setShortLeave(int shortLeave) {
		this.shortLeave = shortLeave;
	}

}
