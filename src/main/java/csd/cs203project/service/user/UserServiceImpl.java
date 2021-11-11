package csd.cs203project.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import csd.cs203project.model.User;
import csd.cs203project.repository.user.UserRepository;

import java.security.SecureRandom;
import java.util.Base64;


@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findByShopShopType(String typeOfShop) {
        return userRepository.findByShopShopType(typeOfShop);
    }

    @Override
    public User addUser(User user) {
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        if (u.isPresent()) {
            return null;
        }

        String telegramSignUpToken = generateSignUpToken(user.getId());
        user.setTelegramSignUpToken(telegramSignUpToken);

        return userRepository.save(user);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).map(user -> {
            return user;
        }).orElse(null);
    }


    @Override
    public User updateUser(String email, User newUserInfo) {
        Optional<User> u = userRepository.findByEmail(email);
        if (u.isPresent()){
            User user = u.get();
            user.setName(newUserInfo.getName());
            user.setVaccinationStatus(newUserInfo.getVaccinationStatus());
            user.setSwabTestResult(newUserInfo.getSwabTestResult());
            user.setFetStatus(newUserInfo.getFetStatus());
            user.setCompany(newUserInfo.getCompany());


            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public void deleteUser(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            userRepository.deleteByEmail(email);
        } else {
            throw new EmptyResultDataAccessException(1); 
        }
    }

    @Override
    public List<User> findEmployeesByCompany(String company) {
        return userRepository.findEmployeesByCompany(company, "Employee");
    }

    @Override
    public List<User> findEmployeesAndAdminsUnderCompany(String company) {
        return userRepository.findEmployeesByCompany(company, "Supervisor");
    }

    public String generateSignUpToken(Long userId) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    
}
