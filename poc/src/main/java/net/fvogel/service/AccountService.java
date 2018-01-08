package net.fvogel.service;

import net.fvogel.model.Account;
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

    public void createAccount(Account accountData) {
        if (accountData == null || !accountData.getPassword().equals(accountData.getPasswordConfirm())) {
            throw new IllegalArgumentException("Password and password confirmation don't match");
        }
        Account account = new Account();
        account.setUserName(accountData.getUserName());
        account.setPassword(passwordEncoder.encode(accountData.getPassword()));
        account.setEmail(accountData.getEmail());
        accountRepository.save(account);
    }

}
