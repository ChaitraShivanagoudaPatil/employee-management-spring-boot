package com.example.employee_management.dto;

import com.example.employee_management.entity.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EmployeeRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,
        @NotNull(message = "Salary is required")
        @Positive(message = "salary must be greater than zero")
        Double salary,
        @NotNull(message = "Department Id is required")
        @Positive(message = "Department Id must be greater than zero")
        Long departmentId

) {

}
