package com.example.employee_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    private double salary;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="department_id",nullable = false)
    private Department department;

}
