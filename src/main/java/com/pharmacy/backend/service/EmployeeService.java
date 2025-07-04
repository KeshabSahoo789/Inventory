package com.pharmacy.backend.service;

import com.pharmacy.backend.dto.EmployeeDto;
import com.pharmacy.backend.model.Employee;
import com.pharmacy.backend.repository.EmployeeRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Synchronized
    public void saveEmployee(EmployeeDto dto) {

        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Employee with this email already exists");
        }

        // Save to MySQL
        Employee employee = new Employee();
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setPassword(dto.getPassword());
        employee.setEmployeeId("EMP" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());

        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public boolean deleteByEmployeeId(String empId) {
        Employee emp = employeeRepository.findByEmployeeId(empId);
        if (emp != null) {
            employeeRepository.delete(emp);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}
