package csd.cs203project.controller.supervisor;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.validation.Valid;

import csd.cs203project.model.*;
import csd.cs203project.service.supervisor.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SupervisorController {
    private SupervisorService supervisorService;

    @Autowired
    public SupervisorController(SupervisorService supervisorService){
        this.supervisorService = supervisorService;
    }

    @GetMapping("/employees/{company}")
    public List<User> getEmployeesUnderCompany(@PathVariable (value = "company") String company){
        return supervisorService.findEmployeesByCompany(company);
    }

    @GetMapping("/employees/administrator/{company}")
    public List<User> getEmployeesAndAdminsUnderCompany(@PathVariable (value = "company") String company) {
        System.out.println(company);
        return supervisorService.findEmployeesAndAdminsUnderCompany(company);
    }

    @PostMapping("/employees")
    public User addEmployee(@RequestBody User employee) {
        System.out.println(employee);
        return supervisorService.addEmployee(employee);
    }

    @PutMapping("/employees/{email}")
    public User updateEmployee(@PathVariable String email, @RequestBody User newEmployeeInfo) {
        return supervisorService.updateEmployee(email, newEmployeeInfo);
    }

    @DeleteMapping("/employees/{email}")
    public void deleteEmployee(@PathVariable String email) {
        try {
            supervisorService.deleteEmployee(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    
}