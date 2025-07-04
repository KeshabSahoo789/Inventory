package com.pharmacy.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeDto {
    private String fullName;
    @NotBlank
    @Email
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private LocalDate joiningDate;
    private String password;
    private String confirmPassword;
}