package csd.cs203project.controller.admin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import javax.validation.Valid;

import csd.cs203project.model.*;
import csd.cs203project.service.admin.AdminService;
import csd.cs203project.service.supervisor.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AdminController {
    private AdminService adminService;

    // @Autowired
    // public AdminController(AdminService adminService){
    //     this.adminService = adminService;
    // }

    // @GetMapping("/admin/{company}")
    // public List<User> getSupervisorsAndEmployeesUnderCompany(@PathVariable (value = "company") String company){
    //     return adminService.findSupervisorsAndEmployeesUnderCompany(company);
    // }


}
