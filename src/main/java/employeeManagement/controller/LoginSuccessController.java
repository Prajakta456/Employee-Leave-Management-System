package employeeManagement.controller;

import employeeManagement.entity.Employee;
import employeeManagement.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginSuccessController {

    private final EmployeeService employeeService;

    public LoginSuccessController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/login-success")
    public String loginSuccess(
            Authentication authentication,
            Model model) {

        int employeeId =
                Integer.parseInt(authentication.getName());

        try {

            Employee employee =
                    employeeService.findEmployeeById(employeeId);

            boolean isAdmin =
                    authentication.getAuthorities()
                            .stream()
                            .anyMatch(authority ->
                                    "ROLE_ADMIN".equals(
                                            authority.getAuthority()));

            if (isAdmin) {

                model.addAttribute(
                        "employees",
                        employeeService.getAllEmployee());

                return "employeelist";
            }

            model.addAttribute(
                    "employee",
                    employee);

            return "employeeDetails";

        } catch (Exception e) {

            return "redirect:/";
        }
    }
}