package com.example.employee_management.dto;

public record EmployeeResponse(long id,String name,String email,
                               double salary,Long departmentId,String departmentName)
{

}
