package com.example.employeemanager.service;

import com.example.employeemanager.exception.UserNotFoundException;
import com.example.employeemanager.model.Employee;
import com.example.employeemanager.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee) {

        boolean validateEmail = isValidEmail(employee.getEmail());
        boolean validatePhone = isValidNumber(employee.getPhone());
        boolean validateUser = isUserExits(employee);



        if (validateEmail && validatePhone && validateUser){
            employee.setEmployeeCode(UUID.randomUUID().toString());
            return employeeRepo.save(employee);
        }
        else {
            return null;
        }
    }

//    public boolean validateEmail(String email){
//        boolean f= false;
//        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
//        email.matches(regex);
//        if ()
//
//    }
    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    private boolean isValidNumber(String phone) {
        String regex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
        return phone.matches(regex);
    }
    private boolean isUserExits(Employee employee){
        Employee employee1 = employeeRepo.findEmployeeByEmail(employee.getEmail());
        if (employee1 == null){
            return true;
        }
        return false;
    }

    public ArrayList<Employee> findAllEmployees() {
        ArrayList<Employee> allEmployee = employeeRepo.findAllEmployee();
        return allEmployee;
    }

    public Employee updateEmployee(Employee employee) {
         employeeRepo.updateEmployee(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getJobTitle(),
                employee.getPhone(),
                employee.getImageUrl(),
                employee.getEmployeeCode());
         return null;
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepo.findEmployeeById(id)
                .orElseThrow(() -> new UserNotFoundException("Employee By id " + id + " Not Found"));
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteEmployeeById(id);
    }
}
