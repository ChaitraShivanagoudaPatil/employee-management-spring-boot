package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.DuplicateEmailException;
import com.example.employee_management.exception.ResourceNotFoundException;
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

    public Employee saveEmployee(EmployeeRequest request) {
        if(employeeRepository.existsByEmail(request.email())){
            throw new DuplicateEmailException("Email already exists");
        }
        Department department=departmentRepository.findById(request.departmentId())
                .orElseThrow(()->new ResourceNotFoundException("Department not found"));
        Employee employee=new Employee();
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setSalary(request.salary());
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    private Department findRequestedDepartment(EmployeeRequest employeeRequest){
        return departmentRepository.findById(employeeRequest.departmentId())
                .orElseThrow(()->new ResourceNotFoundException("Department with this id not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Employee with this id not found"));
    }
   @Transactional
    public Employee updateEmployee(Long id, EmployeeRequest employeeRequest) {
       if(employeeRepository.existsByEmailAndIdNot(employeeRequest.email(),id)){
           throw new DuplicateEmailException("Email already exists");
       }
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee with this id not found"));
        employee.setName(employeeRequest.name());
        employee.setEmail(employeeRequest.email());
        employee.setSalary(employeeRequest.salary());
        employee.setDepartment(findRequestedDepartment(employeeRequest));
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

    @Transactional(rollbackFor = Exception.class)
    public void testSalaryRollBack(Long firstId,Long secondId) throws Exception {
        if(firstId.equals(secondId)){
            throw new IllegalArgumentException("Use two different employees");
        }
        Employee first=employeeRepository.findById(firstId)
                .orElseThrow(()->new ResourceNotFoundException("First Employee not found"));
        Employee second=employeeRepository.findById(secondId)
                .orElseThrow(()->new ResourceNotFoundException(
                        "Second Employee not found"
                ));
        first.setSalary(first.getSalary()+1000);
        second.setSalary(second.getSalary()+2000);
        employeeRepository.flush();
        throw new Exception("Intentional Checked Exception");

    }
}
