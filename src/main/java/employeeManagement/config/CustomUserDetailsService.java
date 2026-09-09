package employeeManagement.security;

import employeeManagement.entity.Employee;
import employeeManagement.entity.Role;
import employeeManagement.repository.EmployeeRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(
            EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        int employeeId;

        try {

            employeeId =
                    Integer.parseInt(username);

        } catch (NumberFormatException e) {

            throw new UsernameNotFoundException(
                    "Invalid employee ID: " + username);
        }

        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Employee not found: "
                                                + employeeId));

        List<SimpleGrantedAuthority> authorities =
                employee.getRoles()
                        .stream()
                        .map(Role::getRoleName)
                        .map(role ->
                                new SimpleGrantedAuthority(
                                        "ROLE_" + role))
                        .toList();

        return User.builder()
                .username(
                        String.valueOf(
                                employee.getEmployeeId()))
                .password(employee.getPassword())
                .authorities(authorities)
                .build();
    }
}