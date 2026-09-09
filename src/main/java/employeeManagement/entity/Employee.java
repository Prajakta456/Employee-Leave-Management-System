package employeeManagement.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "EmployeeDetails")
@Getter
@Setter
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;

	@NotNull
	@Size(max = 65)
	private String firstName;

	@NotNull
	@Size(max = 65)
	private String lastName;

	private Double salary;

	@NotNull
	@Email
	@Size(max = 100)
	@Column(unique = true)
	private String email;

	@NotNull
	@Size(max = 64)
	private String password;

	@NotNull
	@Size(max = 20)
	@Column(unique = true)
	private String phoneNo;

	private String gender;

	@NotNull
	@Size(max = 70)
	private String departmentName;

	@Column(length = 200, nullable = false)
	private String address;

	@Column(nullable = false)
	private String birthDate;

	@Column(nullable = false)
	private String nationality;

	@Column(length = 12, nullable = false, unique = true)
	private String adharNo;

	@Column(length = 12, nullable = false, unique = true)
	private String passportNo;

	@Column(length = 10, nullable = false, unique = true)
	private String panNo;

	@Column(nullable = false)
	private String bloodGroup;

	@Column(nullable = false)
	private String infoToRelativeName;

	@Column(nullable = false)
	private String infoToRelativePhoneNo;

	@Column(nullable = false)
	private String infoToColleagueName;

	@Column(nullable = false)
	private String infoToColleaguePhone;

	@ManyToMany
	@JoinTable(
			name = "employee_roles",
			joinColumns = @JoinColumn(name = "employee_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();

	public void addRole(Role role) {
		this.roles.add(role);
	}
}
