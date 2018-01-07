package net.fvogel.service;

import net.fvogel.model.User;
import net.fvogel.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public void registerUser(User userData) {
        User user = new User();
        user.setName(userData.getName());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        user.setEmail(userData.getEmail());
        userRepository.save(user);
    }

    public User getUser(String userName) {
        return userRepository.findByName(userName);
    }

}
