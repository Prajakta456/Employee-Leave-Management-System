ackage com.employee.employeeManagement.entity;import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LeaveInformation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int earnedLeave;
    private int covidLeave;
    private int inicidentalLeave;
    private int leaveWithoutPay;
    private int shortLeave;
}