package com.example.employee_management.controller;

import com.example.employee_management.entity.Department;
import com.example.employee_management.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService){
        this.departmentService=departmentService;
    }
    @PostMapping
    public Department save(@RequestBody Department department){
        return departmentService.save(department);
    }
    @GetMapping
    public List<Department> findAllDepartment(){
        return departmentService.findAllDepartment();
    }
    @GetMapping("/{id}")
    public Department findDepartmentById(@PathVariable Long id){
        return departmentService.findDepartmentById(id);
    }
    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id,@RequestBody Department departmentDetails){
        return departmentService.updateDepartment(id,departmentDetails);
    }
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id){
        departmentService.deleteDepartment(id);
    }
}
