package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }
    @PostMapping
    public Employee save(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }
    @GetMapping
    public List<EmployeeResponse> findAllEmployees(){
        return employeeService.findAllEmployees();
    }
    @GetMapping("/{id}")
    public Employee findAnEmployeeById(@PathVariable Long id){
        return employeeService.findAnEmployeeById(id);
    }
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
        return employeeService.updateEmployee(id,employeeDetails);
    }
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
    }
    @GetMapping("/department/{departmentId}")
    public List<Employee> findEmployeeByDepartmentId(@PathVariable Long departmentId){
    return employeeService.findEmployeeByDepartmentId(departmentId);
    }
    @GetMapping("/search")
    public List<Employee> findEmployeeByName(@RequestParam String name){
        return employeeService.findEmployeeByName(name);
    }
    @GetMapping("/salary")
    public List<Employee> findBySalaryGreaterThan(@RequestParam double salary){
        return employeeService.findBySalaryGreaterThan(salary);
    }
    @GetMapping("/department/salary")
    public List<Employee> findByDepartmentIdAndSalaryGreaterThan(@RequestParam Long departmentId,@RequestParam double salary){
        return employeeService.findByDepartmentIdAndSalaryGreaterThan(departmentId,salary);
    }
    @GetMapping("/paged")
    public List<EmployeeResponse> fineEmployeePage(@RequestParam int page,@RequestParam int size){
        return employeeService.findEmployeesPage(page,size);
    }
}
