package net.fvogel.service;

import java.util.HashSet;

import net.fvogel.model.Account;
import net.fvogel.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserName(username);
        if (account == null) {
            throw new UsernameNotFoundException("No account with name '" + username + "' found");
        }
        UserDetails userDetails = new User(
                account.getUserName(),
                account.getPassword(),
                new HashSet<>()
        );
        return userDetails;
    }

}
