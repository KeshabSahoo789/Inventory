package com.pharmacy.backend.controller;

import com.pharmacy.backend.dto.EmployeeDto;
import com.pharmacy.backend.model.Employee;
import com.pharmacy.backend.service.CartService;
import com.pharmacy.backend.service.EmployeeService;
import com.pharmacy.backend.service.GeneralProductService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private CartService cartService;

    @Autowired
    private GeneralProductService generalProductService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeDto", new EmployeeDto());
        return "employee-add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute EmployeeDto employeeDto, Model model) {
        try {
            employeeService.saveEmployee(employeeDto);
            return "redirect:/employee/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("employeeDto", employeeDto);
            return "employee-add";
        }
    }

    @GetMapping("/deleteemp")
    public String showDeleteForm(Model model) {
        model.addAttribute("employeeId", "");
        return "employee-delete";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") String empId, Model model) {
        boolean deleted = employeeService.deleteByEmployeeId(empId);

        if (!deleted) {
            model.addAttribute("error", "No employee found with ID: " + empId);
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "employee-list";
        }

        return "redirect:/employee/list";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        return "employee-login";
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "employee-dashboard";
    }

    @PostMapping("/login")
    public String employeeLogin(@ModelAttribute("employee") EmployeeDto loginDto,
                                Model model,
                                HttpSession session) {
        log.info("Login API called for employee.");

        Optional<Employee> empOpt = employeeService.findByEmail(loginDto.getEmail());

        if (empOpt.isEmpty() || !empOpt.get().getPassword().equals(loginDto.getPassword())) {
            model.addAttribute("error", "Invalid email or password.");
            return "employee-login";
        }

        // Clear cart
        cartService.clearCartByEmail(loginDto.getEmail());

        // Set session attributes
        session.setAttribute("employee", empOpt.get());
        session.setAttribute("employeeEmail", loginDto.getEmail());

        return "redirect:/employee/dashboard";
    }
}
