package com.pharmacy.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String employeeId;

    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private LocalDate joiningDate;
    private String password;
}
