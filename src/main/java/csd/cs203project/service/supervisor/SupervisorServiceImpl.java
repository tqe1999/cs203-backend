package csd.cs203project.service.supervisor;

import csd.cs203project.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;

import csd.cs203project.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
@Service
public class SupervisorServiceImpl implements SupervisorService {
    private UserRepository userRepository;
    
    @Autowired
    public SupervisorServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findEmployeesByCompany(String company) {
        return userRepository.findEmployeesByCompany(company, "Admin");
    }

    @Override
    public List<User> findEmployeesAndAdminsUnderCompany(String company) {
        return userRepository.findEmployeesByCompany(company, "NULL");
    }

    @Override
    public User addEmployee(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateEmployee(String email, User newEmployeeInfo) {
        Optional<User> u = userRepository.findByEmail(email);
        System.out.println(u);
        System.out.println(newEmployeeInfo);
        if (u.isPresent()){
            User user = u.get();
            user.setName(newEmployeeInfo.getName());
            user.setVaccinationStatus(newEmployeeInfo.getVaccinationStatus());
            user.setSwabTestResult(newEmployeeInfo.getSwabTestResult());
            user.setFetStatus(newEmployeeInfo.getFetStatus());
            user.setCompany(newEmployeeInfo.getCompany());

            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public void deleteEmployee(String email) {
        userRepository.deleteByEmail(email);
    }


}
