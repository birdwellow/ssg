package net.fvogel.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fvogel.model.Account;
import net.fvogel.model.AccountRegistration;
import net.fvogel.model.Login;
import net.fvogel.service.AccountService;
import net.fvogel.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    AccountService accountService;

    @Autowired
    SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public void register(@RequestBody AccountRegistration registration, HttpServletRequest request) {
        String password = registration.getPassword();
        Account account = accountService.createAccountFromRegistration(registration);
        securityService.login(account.getUserName(), password, request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public void login(@RequestBody Login login, HttpServletRequest request) {
        securityService.login(login, request);
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        securityService.logout(request, response);
    }

}
