package com.example.employeemanager.controller;

import com.example.employeemanager.model.Employee;
import com.example.employeemanager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Controller
public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public String employeeDashboard(Model model){
        ArrayList<Employee> allEmployee = employeeService.findAllEmployees();
        model.addAttribute("allEmployee", allEmployee);
        return "employee";
    }

    @GetMapping("/allTable")
    public String getAllEmployees(Model model){
        ArrayList<Employee> allEmployee = employeeService.findAllEmployees();
        model.addAttribute("allEmployee", allEmployee);
        return "employeeList";
    }

    @PostMapping("/find/{id}")
    public String  getEmployeeById( @PathVariable("id") Long id,
                                    Employee employee, Model model){
        Employee foundEmployee = employeeService.findEmployeeById(id);
//        Employee employee = employeeService.findEmployeeById(id);
        model.addAttribute("foundEmployee", foundEmployee);
        return "employee";
    }

    /*ADD segment*/
    @GetMapping("/addEmployee")
    public String addEmployeeForm(Model model) {

        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "addEmployee";
    }
    @PostMapping("/addedEmployee")
    public String addEmployee(@ModelAttribute("employee") Employee employee,
                              BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "addEmployee";
        }
        else {
            employeeService.addEmployee(employee);
            String msg = "Added Employee";
            model.addAttribute("successMessage", msg);
            model.addAttribute("employee",new Employee());
            return "redirect:/allTable";
        }
    }
    /*ADD segment*/

/*    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee newEmployee = employeeService.addEmployee(employee);
        if (newEmployee == null){
            return new ResponseEntity<>(newEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }*/

/*EDIT and update segment*/
    @RequestMapping(value= "edit/{id}", method = RequestMethod.GET)
    public String editPerson(@PathVariable("id") Long id, ModelMap model) {
        model.put("editEmployee", employeeService.findEmployeeById(id));
        return  "editEmployee";
    }


    @RequestMapping(value="/update",method=RequestMethod.POST)
    public String updatePerson(@ModelAttribute("editEmployee") Employee employeeToBeUpdated) {
        employeeService.updateEmployee(employeeToBeUpdated);
        return "redirect:/allTable";
    }
    /*EDIT and update segment*/
/*
    @PutMapping("/edit/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }*/
    /*DELETE segment*/
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable("id") Long id, ModelMap model){
        model.put("deletePerson", employeeService.findEmployeeById(id));
        employeeService.deleteEmployee(id);
        return "redirect:/allTable";
    }


/*
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

}