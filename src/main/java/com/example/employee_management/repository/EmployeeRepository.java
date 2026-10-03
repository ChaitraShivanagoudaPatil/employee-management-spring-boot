package com.example.employee_management.repository;

import ch.qos.logback.core.model.conditional.ElseModel;
import com.example.employee_management.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long
        > {

    List<Employee> findByDepartmentId(Long departmentId);

    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findBySalaryGreaterThan(double salary);

    List<Employee> findByDepartmentIdAndSalaryGreaterThan(Long departmentId, double salary);

    List<Employee> findBySalaryBetween(double minSalary, double maxSalary);

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email,Long id);

    long countByDepartmentId(Long departmentId);


    @Query("""
            SELECT e FROM Employee e WHERE e.salary > :salary
            """)
    List<Employee> findHighEarners(@Param("salary") double salary);

    @Query("""
            SELECT e from Employee e WHERE e.department.id =:departmentId AND e.salary>:salary ORDER BY e.salary DESC
            """)
    List<Employee> findMatchedEmployee(@Param("departmentId") Long departmentId, @Param("salary") double salary);

    @Query(value = """
            SELECT e.* 
            FROM employees e 
            WHERE e.department_id=:departmentId 
            AND e.salary> :salary 
            ORDER BY e.salary DESC
            """, nativeQuery = true)
    List<Employee> findmatchedEmployeeNative(@Param("departmentId") Long departmentId, @Param("salary") double salary);
    @Query("""
SELECT e FROM Employee e
JOIN FETCH e.department
""")
    List<Employee> findAllWithDepartment();
    @Override
    @EntityGraph(attributePaths = {"department"})
    List<Employee> findAll();
    @Override
    @EntityGraph(attributePaths = {"department"})
    Page<Employee> findAll(Pageable pageable);

}
