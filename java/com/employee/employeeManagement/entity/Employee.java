package com.employee.employeeManagement.entity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
@Entity annotation defines that a class can be mapped to a table. And that is it, it is just a marker, like for example Serializable interface.
*/
@Entity
@Table(name = "EmployeeDetails")
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
			@Email
			@Size(max = 20)
			@Column(unique = true)
			private String phoneNo;
			
			private String gender;
			
			@NotNull
			@Size(max = 70)
			private String departmentName;


			@Column(length=200,nullable=false)
			private String address;
			
			@Column(nullable=false)
			private String birthDate;
			
			@Column(nullable=false)
			private String nationality;
			
			@Column(length=12,nullable=false,unique=true)
			private String adharNo;
			
			@Column(length=12,nullable=false,unique=true)
			private String passportNo;
			
			@Column(length=10,nullable=false,unique=true)
			private String panNo;
			
			@Column(nullable=false)
			private String bloodGroup;
			
			@Column(nullable=false)
			private String infoToRelativeName;
			
			@Column(nullable=false)
			private String infoToRelativePhoneNo;
			
			@Column(nullable=false)
			private String infoToColleagueName;
			
			@Column(nullable=false)
			private String infoToColleaguePhone;
			
			@ManyToMany
			
			@JoinTable(name="employee_roles",
			joinColumns=@JoinColumn(name="employee_id"),
			inverseJoinColumns=@JoinColumn(name="role_id"))
			private Set<Role> roles;
			
			public void addRole(Role obj) {
				this.roles.add(obj);
			}
			
			public Set<Role> getRoles() {
				return roles;
			}

			public void setRoles(Set<Role> roles) {
				this.roles = roles;
			}	
			
			public int getEmployeeId() {
				return employeeId;
			}

			public void setEmployeeId(int employeeId) {
				this.employeeId = employeeId;
			}

			public String getFirstName() {
				return firstName;
			}

			public void setFirstName(String firstName) {
				this.firstName = firstName;
			}

			public String getLastName() {
				return lastName;
			}

			public void setLastName(String lastName) {
				this.lastName = lastName;
			}

			public Double getSalary() {
				return salary;
			}

			public void setSalary(Double salary) {
				this.salary = salary;
			}

			public String getEmail() {
				return email;
			}

			public void setEmail(String email) {
				this.email = email;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}

			public String getPhoneNo() {
				return phoneNo;
			}

			public void setPhoneNo(String phoneNo) {
				this.phoneNo = phoneNo;
			}

			public String getGender() {
				return gender;
			}

			public void setGender(String gender) {
				this.gender = gender;
			}

			public String getDepartmentName() {
				return departmentName;
			}

			public void setDepartmentName(String departmentName) {
				this.departmentName = departmentName;
			}

			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getBirthDate() {
				return birthDate;
			}

			public void setBirthDate(String birthDate) {
				this.birthDate = birthDate;
			}

			public String getNationality() {
				return nationality;
			}

			public void setNationality(String nationality) {
				this.nationality = nationality;
			}

			public String getAdharNo() {
				return adharNo;
			}

			public void setAdharNo(String adharNo) {
				this.adharNo = adharNo;
			}

			public String getPassportNo() {
				return passportNo;
			}

			public void setPassportNo(String passportNo) {
				this.passportNo = passportNo;
			}

			public String getPanNo() {
				return panNo;
			}

			public void setPanNo(String panNo) {
				this.panNo = panNo;
			}

			public String getBloodGroup() {
				return bloodGroup;
			}

			public void setBloodGroup(String bloodGroup) {
				this.bloodGroup = bloodGroup;
			}

			public String getInfoToRelativeName() {
				return infoToRelativeName;
			}

			public void setInfoToRelativeName(String infoToRelativeName) {
				this.infoToRelativeName = infoToRelativeName;
			}

			public String getInfoToRelativePhoneNo() {
				return infoToRelativePhoneNo;
			}

			public void setInfoToRelativePhoneNo(String infoToRelativePhoneNo) {
				this.infoToRelativePhoneNo = infoToRelativePhoneNo;
			}

			public String getInfoToColleagueName() {
				return infoToColleagueName;
			}

			public void setInfoToColleagueName(String infoToColleagueName) {
				this.infoToColleagueName = infoToColleagueName;
			}

			public String getInfoToColleaguePhone() {
				return infoToColleaguePhone;
			}

			public void setInfoToColleaguePhone(String infoToColleaguePhone) {
				this.infoToColleaguePhone = infoToColleaguePhone;
			}

		
}

	

