package com.employee.employeeManagement.service;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.employee.employeeManagement.entity.Employee;
import com.employee.employeeManagement.entity.Role;
import com.employee.employeeManagement.exception.EmployeeNotFoundException;
import com.employee.employeeManagement.repository.EmployeeRepository;
import com.employee.employeeManagement.repository.RoleRepo;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final RoleRepo roleRepo;

    public EmployeeService(EmployeeRepository employeeRepo,
                           RoleRepo roleRepo) {
        this.employeeRepo = employeeRepo;
        this.roleRepo = roleRepo;
    }

    // Get all employees
    public List<Employee> getAllEmployee() {
        return (List<Employee>) employeeRepo.findAll();
    }

    // Get employee by ID
    @Cacheable(value = "employees", key = "#id")
    public Employee findEmployeeById(int id)
            throws EmployeeNotFoundException {

        System.out.println(
                "Fetching employee from DB: " + id);

        return employeeRepo.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Could not find any employee with ID "
                                        + id));
    }

    // Save or update employee
    //
    // After saving:
    // employees[employeeId] = updated employee
    //
    // This means the cache is updated immediately.
    @CachePut(
            value = "employees",
            key = "#result.employeeId"
    )
    public Employee saveEmployee(Employee employee)
            throws EmployeeNotFoundException {

        // Existing employee
        if (employee.getEmployeeId() != 0) {

            Employee existingEmployee =
                    employeeRepo.findById(
                                    employee.getEmployeeId())
                            .orElseThrow(() ->
                                    new EmployeeNotFoundException(
                                            "Could not find any employee with ID "
                                                    + employee.getEmployeeId()));

            // Keep the existing password if no new password
            // was entered during update.
            if (employee.getPassword() == null
                    || employee.getPassword().isEmpty()) {

                employee.setPassword(
                        existingEmployee.getPassword());
            }
        }

        return employeeRepo.save(employee);
    }

    // Get all roles
    @Cacheable(
            value = "roles",
            key = "'all'"
    )
    public List<Role> getRoles() {

        System.out.println(
                "Fetching roles from DB");

        return (List<Role>) roleRepo.findAll();
    }

    // Check login credentials
    public boolean isLoginSuccessful(
            int id,
            String password)
            throws EmployeeNotFoundException {

        if (id <= 0 || password == null) {
            return false;
        }

        Employee employee =
                findEmployeeById(id);

        return password.equals(
                employee.getPassword());
    }
}
