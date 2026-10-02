package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository,DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository=departmentRepository;
    }

    public Employee saveEmployee(Employee employee) {
        Department department=findRequestedDepartment(employee);
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    private Department findRequestedDepartment(Employee employee){
        if(Objects.isNull(employee.getDepartment())|| Objects.isNull(employee.getDepartment().getId())){
            throw new IllegalArgumentException("Department ID is required");
        }
        Long departmentId=employee.getDepartment().getId();
        return departmentRepository.findById(departmentId)
                .orElseThrow(()->new RuntimeException("Department with this id not found"));

    }
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAllEmployees() {

        return employeeRepository.findAll()
                .stream().map(employee -> new EmployeeResponse(employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getSalary(),
                        employee.getDepartment().getId(),
                        employee.getDepartment().getName()))
                .toList();
    }

    public Employee findAnEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee with this id not found"));
    }
   @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Employee with this id not found"));
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setSalary(employeeDetails.getSalary());
        employee.setDepartment(findRequestedDepartment(employeeDetails));
        return employee;
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
    public List<Employee> findEmployeeByDepartmentId(Long departmentId){
        return employeeRepository.findByDepartmentId(departmentId);
    }

    public List<Employee> findEmployeeByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }
    public List<Employee> findBySalaryGreaterThan(double salary){
        return employeeRepository.findBySalaryGreaterThan(salary);
    }

    public List<Employee> findByDepartmentIdAndSalaryGreaterThan(Long departmentId, double salary) {
        return employeeRepository.findByDepartmentIdAndSalaryGreaterThan(departmentId,salary);
    }
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findEmployeesPage(int page, int size){
        Pageable pageable= PageRequest.of(
               page,size, Sort.by(Sort.Direction.DESC,"salary").and(Sort.by("id"))
        );
        return employeeRepository.findAll(pageable).stream()
                .map(employee->new EmployeeResponse(employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getSalary(),
                        employee.getDepartment().getId(),
                        employee.getDepartment().getName())).toList();
    }
}
