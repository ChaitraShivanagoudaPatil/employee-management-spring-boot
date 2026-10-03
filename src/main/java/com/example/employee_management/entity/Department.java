package com.example.employee_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Employee> employeeList=new ArrayList<>();
    public void addEmployee(Employee employee){
        employeeList.add(employee);
        employee.setDepartment(this);
    }
    public void removeEmployee(Employee employee){
        employeeList.remove(employee);
        employee.setDepartment(null);
    }
}
