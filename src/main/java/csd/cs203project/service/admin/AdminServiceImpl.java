package csd.cs203project.service.admin;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import csd.cs203project.model.*;
import csd.cs203project.repository.user.UserRepository;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService  {
    private UserRepository userRepository;
    
    @Autowired
    public AdminServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User addValidSupervisor(User user) {
        if (user.getUserType().equals("Supervisor")) {
            return userRepository.save(user);
        } 
        return null;
        
    }

    @Override
    public User updateValidSupervisor(Long id, User user) {
        // return userRepository.findById(id).map(user -> {book.setTitle(newBookInfo.getTitle());
        //     return books.save(book);
        // }).orElse(null);
        // if (user.getUserType().equals("Supervisor")) {
        //     userRepository.save(user);
        // }
        return user;
    }

    // @Override
    // public List<User> findSupervisorsAndEmployeesUnderCompany(String company) {
    //     return userRepository.findEmployeesAndSupervisorsByCompany(company, "Admin");
    // }
}
