package com.pharmacy.backend.repository;

import com.pharmacy.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
    Employee findByEmployeeId(String employeeId);
    Optional<Employee> findByEmail(String email);

}
