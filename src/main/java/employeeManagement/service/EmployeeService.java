package employeeManagement.service;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import employeeManagement.entity.Employee;
import employeeManagement.entity.Role;
import employeeManagement.exception.EmployeeNotFoundException;
import employeeManagement.repository.EmployeeRepository;
import employeeManagement.repository.RoleRepo;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(
            EmployeeRepository employeeRepo,
            RoleRepo roleRepo,
            PasswordEncoder passwordEncoder) {

        this.employeeRepo = employeeRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================================================
    // GET ALL EMPLOYEES
    // =========================================================

    public List<Employee> getAllEmployee() {

        return (List<Employee>) employeeRepo.findAll();
    }

    // =========================================================
    // GET EMPLOYEE BY ID
    // =========================================================

    @Cacheable(
            value = "employees",
            key = "#id"
    )
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

    // =========================================================
    // SAVE OR UPDATE EMPLOYEE
    // =========================================================

    @CachePut(
            value = "employees",
            key = "#result.employeeId"
    )
    public Employee saveEmployee(Employee employee)
            throws EmployeeNotFoundException {

        // -----------------------------------------------------
        // NEW EMPLOYEE
        // -----------------------------------------------------

        if (employee.getEmployeeId() == 0) {

            /*
             * New employee:
             *
             * Convert plain-text password into BCrypt
             * before storing it in the database.
             */
            if (employee.getPassword() != null
                    && !employee.getPassword().isBlank()) {

                employee.setPassword(
                        passwordEncoder.encode(
                                employee.getPassword()
                        )
                );
            }
        }

        // -----------------------------------------------------
        // EXISTING EMPLOYEE
        // -----------------------------------------------------

        else {

            Employee existingEmployee =
                    employeeRepo.findById(
                            employee.getEmployeeId()
                    ).orElseThrow(() ->
                            new EmployeeNotFoundException(
                                    "Could not find any employee with ID "
                                            + employee.getEmployeeId()
                            )
                    );

            /*
             * If the password field is empty during update,
             * keep the existing BCrypt password.
             */
            if (employee.getPassword() == null
                    || employee.getPassword().isBlank()) {

                employee.setPassword(
                        existingEmployee.getPassword()
                );
            }

            /*
             * If a new password was entered during update,
             * encode it with BCrypt.
             *
             * We don't encode the existing BCrypt password
             * again because the existing password is only
             * copied when the field is empty.
             */
            else {

                employee.setPassword(
                        passwordEncoder.encode(
                                employee.getPassword()
                        )
                );
            }
        }

        return employeeRepo.save(employee);
    }

    // =========================================================
    // GET ALL ROLES
    // =========================================================

    @Cacheable(
            value = "roles",
            key = "'all'"
    )
    public List<Role> getRoles() {

        System.out.println(
                "Fetching roles from DB");

        return (List<Role>) roleRepo.findAll();
    }

    // =========================================================
    // CHECK LOGIN CREDENTIALS
    // =========================================================

    /*
     * This method may no longer be used by Spring Security,
     * because CustomUserDetailsService handles authentication.
     *
     * However, if another part of your old application calls
     * this method, BCrypt must be checked using matches().
     */
    public boolean isLoginSuccessful(
            int id,
            String password)
            throws EmployeeNotFoundException {

        if (id <= 0
                || password == null
                || password.isBlank()) {

            return false;
        }

        Employee employee =
                findEmployeeById(id);

        return passwordEncoder.matches(
                password,
                employee.getPassword()
        );
    }
}