package employeeManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

