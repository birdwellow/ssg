package net.fvogel.service;

import java.util.HashSet;

import net.fvogel.model.User;
import net.fvogel.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            return null;
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                new HashSet<>()
        );
        return userDetails;
    }

}
