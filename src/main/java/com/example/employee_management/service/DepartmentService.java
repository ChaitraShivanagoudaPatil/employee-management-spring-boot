package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeResponse;
import com.example.employee_management.entity.Department;
import com.example.employee_management.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository=departmentRepository;
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> findAllDepartment() {
        return departmentRepository.findAll();
    }

    public Department findDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Department with that id not found"));
    }
    @Transactional
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department=departmentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Department with this id not found"));
        department.setName(departmentDetails.getName());
        return department;
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findDepartmentEmployees(Long id){
        Department department=departmentRepository.findById(
                id
        ).orElseThrow(()->new RuntimeException("Department not found"));
        return department.getEmployeeList().stream().map(
                employee -> new EmployeeResponse(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getSalary(),
                        department.getId(),
                        department.getName()
                )).toList();
    }
}
