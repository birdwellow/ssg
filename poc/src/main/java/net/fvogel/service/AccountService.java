package net.fvogel.service;

import net.fvogel.model.Account;
import net.fvogel.model.AccountRegistration;
import net.fvogel.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    SecurityService securityService;

    public Account getCurrentAccount() {
        String userName = securityService.getCurrentSessionUserName();
        return accountRepository.findByUserName(userName);
    }

    public Account createAccountFromRegistration(AccountRegistration registration) {
        if (registration == null || !registration.getPassword().equals(registration.getPasswordConfirmation())) {
            throw new IllegalArgumentException("Password and password confirmation don't match");
        }
        if (accountRepository.findByUserName(registration.getUserName()) != null) {
            throw new IllegalArgumentException("User name '" + registration.getUserName() + "' already exists");
        }
        if (accountRepository.findByEmail(registration.getEmail()) != null) {
            throw new IllegalArgumentException("An account for email address '" + registration.getEmail() + "' is already registered");
        }
        Account account = new Account();
        account.setUserName(registration.getUserName());
        account.setPassword(passwordEncoder.encode(registration.getPassword()));
        account.setEmail(registration.getEmail());
        accountRepository.save(account);

        return account;
    }

}
