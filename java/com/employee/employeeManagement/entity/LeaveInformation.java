package com.employee.employeeManagement.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LeaveInformation")
public class LeaveInformation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int earnedLeave;
	
	private int covidLeave;
	
	private int inicidentalLeave;	
	
	private int leaveWithoutPay;
	  
	private int shortLeave;

    public LeaveInformation() {
	}

	public LeaveInformation(int earnedLeave, int covidLeave, int inicidentalLeave, int leaveWithoutPay,
			int shortLeave) {
		this.earnedLeave = earnedLeave;
		this.covidLeave = covidLeave;
		this.inicidentalLeave = inicidentalLeave;
		this.leaveWithoutPay = leaveWithoutPay;
		this.shortLeave = shortLeave;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

