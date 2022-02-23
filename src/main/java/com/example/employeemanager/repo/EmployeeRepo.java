package com.example.employeemanager.repo;

import com.example.employeemanager.model.Employee;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    void deleteEmployeeById(Long id);

    Optional<Employee> findEmployeeById(Long id);

    @Query(value = "Select * from employee where email = ?1", nativeQuery = true)
    Employee findEmployeeByEmail(String email);
}
