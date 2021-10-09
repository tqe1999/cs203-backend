package csd.cs203project.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import csd.cs203project.model.User;
import csd.cs203project.service.user.UserService;

@CrossOrigin
@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/email/{email}")
    public User getUser(@PathVariable String email) {
        // User user = new User("joh.doe@smu.edu.sg", "John Doe", "Supervisor", "Compny A");
        
        User user = userService.getUser(email);
        return user;
    }
    
    @PostMapping("/users")
    public void addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
    }

    @PutMapping("/users/email/{email}")
    public User updateUser(@PathVariable String email, @RequestBody User newUserInfo) {
        User user = userService.updateUser(email, newUserInfo);
        if (user == null) return null;
        
        return user;
    }
}
