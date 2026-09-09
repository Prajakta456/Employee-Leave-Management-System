package employeeManagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import employeeManagement.entity.ApplyLeave;
import employeeManagement.entity.Employee;
import employeeManagement.entity.LeaveInfoEmployees;
import employeeManagement.entity.LeaveInformation;
import employeeManagement.entity.Role;
import employeeManagement.exception.EmployeeNotFoundException;
import employeeManagement.service.ApplyLeaveService;
import employeeManagement.service.EmployeeService;
import employeeManagement.service.LeaveEmployeeService;
import employeeManagement.service.LeaveService;

@Controller
public class AdminController {

    private final EmployeeService employeeService;
    private final LeaveEmployeeService leaveEmployeeService;
    private final LeaveService leaveService;
    private final ApplyLeaveService applyLeaveService;

    public AdminController(
            EmployeeService employeeService,
            LeaveEmployeeService leaveEmployeeService,
            LeaveService leaveService,
            ApplyLeaveService applyLeaveService) {

        this.employeeService = employeeService;
        this.leaveEmployeeService = leaveEmployeeService;
        this.leaveService = leaveService;
        this.applyLeaveService = applyLeaveService;
    }

    // ---------------------------------------------------------
    // Employee Management
    // ---------------------------------------------------------

    @GetMapping("/employees")
    public String listEmployee(Model model) {

        List<Employee> employees = employeeService.getAllEmployee();

        model.addAttribute("employees", employees);

        return "employeelist";
    }

    @GetMapping("/employees/new")
    public String newEmployee(Model model) {

        List<Role> roles = employeeService.getRoles();

        Employee employee = new Employee();

        model.addAttribute("employee", employee);
        model.addAttribute("listRoles", roles);
        model.addAttribute("title", "Add new Employee");

        return "employeeform";
    }

    @PostMapping("/employees/save")
    public String saveEmployee(
            Employee employee,
            RedirectAttributes redirectAttributes)
            throws EmployeeNotFoundException {

        employeeService.saveEmployee(employee);

        redirectAttributes.addFlashAttribute(
                "message",
                "Employee has been saved successfully");

        return "redirect:/employees";
    }

    // ---------------------------------------------------------
    // Leave Information
    // ---------------------------------------------------------

    @GetMapping("/employees/Setleave")
    public String enterLeaveDetails(Model model) {

        model.addAttribute("leave", new LeaveInformation());

        return "enterLeaveDetails";
    }

    @PostMapping("/employees/leave/save")
    public String saveLeaveInformation(
            Model model,
            LeaveInformation leaveInformation) {

        leaveService.saveLeaveDetails(leaveInformation);

        model.addAttribute("leave", leaveInformation);

        return "leaveDetails";
    }

    // Employee can view available leave details
    @GetMapping("/employees/leave/view/{empId}")
    public String viewLeaveDetails(
            Model model,
            @PathVariable(name = "empId") int empId)
            throws EmployeeNotFoundException {

        Employee employee = employeeService.findEmployeeById(empId);

        LeaveInformation leaveInformation =
                leaveService.getLeaveDetails();

        if (leaveInformation == null) {

            model.addAttribute("employee", employee);
            model.addAttribute(
                    "message",
                    "Leave Details are not yet set");

            return "employeeDetails";
        }

        LeaveInfoEmployees employeeLeave =
                leaveEmployeeService.getEmployeeLeaveDetails(empId);

        if (employeeLeave == null) {

            model.addAttribute("employee", employee);
            model.addAttribute(
                    "message",
                    "Leave Details are not yet set");

            return "employeeDetails";
        }

        model.addAttribute("leave", employeeLeave);
        model.addAttribute("employee", employee);

        return "EmployeeleaveDetails";
    }

    // ---------------------------------------------------------
    // Set Leave Balance For Employees
    // ---------------------------------------------------------

    @GetMapping("/leaveDetails/save")
    public String saveLeaveForEmployees(
            RedirectAttributes redirectAttributes,
            Model model) {

        LeaveInformation leaveInformation =
                leaveService.getLeaveDetails();

        if (leaveInformation == null) {

            model.addAttribute(
                    "message",
                    "Please set leave details first");

            List<Employee> employees =
                    employeeService.getAllEmployee();

            model.addAttribute("employees", employees);

            return "employeelist";
        }

        List<Employee> employees =
                employeeService.getAllEmployee();

        for (Employee employee : employees) {

            LeaveInfoEmployees leaveForEmployee =
                    new LeaveInfoEmployees();

            leaveForEmployee.setEmployeeId(
                    employee.getEmployeeId());

            leaveForEmployee.setCovidLeave(
                    leaveInformation.getCovidLeave());

            leaveForEmployee.setEarnedLeave(
                    leaveInformation.getEarnedLeave());

            leaveForEmployee.setInicidentalLeave(
                    leaveInformation.getInicidentalLeave());

            leaveForEmployee.setLeaveWithoutPay(
                    leaveInformation.getLeaveWithoutPay());

            leaveForEmployee.setShortLeave(
                    leaveInformation.getShortLeave());

            leaveEmployeeService.saveLeaveInfo(
                    leaveForEmployee);
        }

        model.addAttribute(
                "message",
                "Leave For Employees set successfully");

        model.addAttribute("employees", employees);

        return "employeelist";
    }

    // Admin can view general leave details
    @GetMapping("/leaveDetails/view")
    public String getLeaveDetails(Model model) {

        LeaveInformation leaveInformation =
                leaveService.getLeaveDetails();

        if (leaveInformation != null) {

            model.addAttribute(
                    "leave",
                    leaveInformation);

            return "leaveDetails";
        }

        model.addAttribute(
                "message",
                "You have not yet set the leave details");

        List<Employee> employees =
                employeeService.getAllEmployee();

        model.addAttribute("employees", employees);

        return "employeelist";
    }

    // ---------------------------------------------------------
    // Employee Leave Application
    // ---------------------------------------------------------

    @GetMapping("/employees/leave/leaveStatus/{employeeId}")
    public String checkStatus(
            Model model,
            @PathVariable(name = "employeeId") int employeeId,
            org.springframework.security.core.Authentication authentication)
            throws EmployeeNotFoundException {

        int loggedInEmployeeId =
                Integer.parseInt(authentication.getName());

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        "ROLE_ADMIN".equals(a.getAuthority()));

        if (!isAdmin && loggedInEmployeeId != employeeId) {
            return "redirect:/";
        }

        List<ApplyLeave> leaves =
                applyLeaveService.findLeaveForEmployeeId(employeeId);

        if (leaves != null && !leaves.isEmpty()) {

            model.addAttribute(
                    "applyLeaves",
                    leaves);

            return "checkLeaveStatus";
        }

        Employee employee =
                employeeService.findEmployeeById(employeeId);

        model.addAttribute(
                "message",
                "You have not applied for leave yet!");

        model.addAttribute(
                "employee",
                employee);

        return "employeeDetails";
    }

    @PostMapping("/employees/apply/save")
    public String saveEmpLeave(
            Model model,
            ApplyLeave applyLeave,
            RedirectAttributes redirectAttributes,
            org.springframework.security.core.Authentication authentication)
            throws EmployeeNotFoundException {

        // IMPORTANT:
        // Do NOT trust employeeId coming from hidden HTML field.
        int employeeId =
                Integer.parseInt(authentication.getName());

        // Set authenticated employee ID
        applyLeave.setEmployeeId(employeeId);

        Employee employee =
                employeeService.findEmployeeById(employeeId);

        LeaveInfoEmployees employeeLeave =
                leaveEmployeeService.getEmployeeLeaveDetails(
                        employeeId);

        if (employeeLeave == null) {

            model.addAttribute(
                    "message",
                    "Leave Details are not yet set");

            model.addAttribute(
                    "employee",
                    employee);

            return "employeeDetails";
        }

        LocalDate currentDate = LocalDate.now();

        String leaveDateString =
                applyLeave.getFromDate();

        LocalDate leaveDate;

        try {

            leaveDate =
                    LocalDate.parse(leaveDateString);

        } catch (Exception e) {

            model.addAttribute(
                    "message",
                    "You did not enter the leave in correct format! Please try again");

            model.addAttribute(
                    "employee",
                    employee);

            return "employeeDetails";
        }

        if (leaveDate.isBefore(currentDate)) {

            model.addAttribute(
                    "message",
                    "You did not enter the leave from date correctly! Please try again");

            model.addAttribute(
                    "employee",
                    employee);

            return "employeeDetails";
        }

        String leaveCategory =
                applyLeave.getLeaveCategory();

        boolean leaveApplied = false;

        switch (leaveCategory) {

            case "Earned Leave":

                if (employeeLeave.getEarnedLeave()
                        >= applyLeave.getNoOfDays()) {

                    leaveApplied = true;
                }

                break;

            case "Covid Leave":

                if (employeeLeave.getCovidLeave()
                        >= applyLeave.getNoOfDays()) {

                    leaveApplied = true;
                }

                break;

            case "Incidental Leave":

                if (employeeLeave.getInicidentalLeave()
                        >= applyLeave.getNoOfDays()) {

                    leaveApplied = true;
                }

                break;

            case "Leave Without Pay":

                if (employeeLeave.getLeaveWithoutPay()
                        >= applyLeave.getNoOfDays()) {

                    leaveApplied = true;
                }

                break;

            case "Short Leave":

                if (employeeLeave.getShortLeave()
                        >= applyLeave.getNoOfDays()) {

                    leaveApplied = true;
                }

                break;

            default:

                model.addAttribute(
                        "message",
                        "Invalid leave category");

                model.addAttribute(
                        "employee",
                        employee);

                return "employeeDetails";
        }

        if (leaveApplied) {

            applyLeave.setLeaveStatus("PENDING");

            applyLeaveService.saveEmployeeLeave(
                    applyLeave);

            model.addAttribute(
                    "message",
                    "You have successfully applied for leave!");

        } else {

            model.addAttribute(
                    "message",
                    "You don't have sufficient leave in this category");
        }

        model.addAttribute(
                "employee",
                employee);

        return "employeeDetails";
    }

    // ---------------------------------------------------------
    // Admin Leave Approval
    // ---------------------------------------------------------

    @GetMapping("/employees/leave/allleaves")
    public String viewLeave(Model model) {

        List<ApplyLeave> pendingLeaves =
                applyLeaveService.getAllPendingLeave();

        model.addAttribute(
                "pendingLeaves",
                pendingLeaves);

        return "viewPendingLeaves";
    }

    @GetMapping(
            "/employees/leave/allleaves/updatepending/{leaveid}")
    public String updatePendingLeave(
            @PathVariable(name = "leaveid") int leaveId,
            RedirectAttributes redirectAttributes) {

        ApplyLeave applyLeave =
                applyLeaveService.getLeaveByLeaveId(leaveId);

        if (applyLeave == null) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "Leave not found");

            return "redirect:/employees/leave/allleaves";
        }

        int employeeId =
                applyLeave.getEmployeeId();

        LeaveInfoEmployees employeeLeave =
                leaveEmployeeService.getEmployeeLeaveDetails(
                        employeeId);

        if (employeeLeave == null) {

            redirectAttributes.addFlashAttribute(
                    "message",
                    "Employee leave details not found");

            return "redirect:/employees/leave/allleaves";
        }

        String leaveCategory =
                applyLeave.getLeaveCategory();

        switch (leaveCategory) {

            case "Earned Leave":

                employeeLeave.setEarnedLeave(
                        employeeLeave.getEarnedLeave()
                                - applyLeave.getNoOfDays());

                break;

            case "Covid Leave":

                employeeLeave.setCovidLeave(
                        employeeLeave.getCovidLeave()
                                - applyLeave.getNoOfDays());

                break;

            case "Incidental Leave":

                employeeLeave.setInicidentalLeave(
                        employeeLeave.getInicidentalLeave()
                                - applyLeave.getNoOfDays());

                break;

            case "Leave Without Pay":

                employeeLeave.setLeaveWithoutPay(
                        employeeLeave.getLeaveWithoutPay()
                                - applyLeave.getNoOfDays());

                break;

            case "Short Leave":

                employeeLeave.setShortLeave(
                        employeeLeave.getShortLeave()
                                - applyLeave.getNoOfDays());

                break;

            default:

                redirectAttributes.addFlashAttribute(
                        "message",
                        "Invalid leave category");

                return "redirect:/employees/leave/allleaves";
        }

        applyLeave.setLeaveStatus("APPROVED");

        leaveEmployeeService.saveLeaveInfo(
                employeeLeave);

        applyLeaveService.saveEmployeeLeave(
                applyLeave);

        redirectAttributes.addFlashAttribute(
                "message",
                "Leave Approved");

        return "redirect:/employees/leave/allleaves";
    }
}