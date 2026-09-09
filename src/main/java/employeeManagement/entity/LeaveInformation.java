package employeeManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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