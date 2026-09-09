```java
		package com.employee.employeeManagement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveInfoEmployees {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int leaveId;

	private int employeeId;

	private int earnedLeave;

	private int covidLeave;

	private int inicidentalLeave;

	private int leaveWithoutPay;

	private int shortLeave;
}
```
