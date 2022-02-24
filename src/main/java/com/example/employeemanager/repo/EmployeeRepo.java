package com.example.employeemanager.repo;

import com.example.employeemanager.model.Employee;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    void deleteEmployeeById(Long id);

    @Query(value = "Select * from employee where id = ?1", nativeQuery = true)
    Optional<Employee> findEmployeeById(Long id);

    @Query(value = "Select * from employee where email = ?1", nativeQuery = true)
    Employee findEmployeeByEmail(String email);

    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    public ArrayList<Employee> findAllEmployee();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE employee u SET u.name=?2 , u.email=?3,  u.job_title=?4, u.phone= ?5, u.image_url= ?6 WHERE u.id=?1", nativeQuery = true)
    void  updateEmployee(Long id,
                            String name,
                            String email,
                            String jobTitle,
                            String phone,
                            String imageUrl,
                            String employeeCode);
}
