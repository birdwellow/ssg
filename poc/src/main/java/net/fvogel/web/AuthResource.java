package net.fvogel.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fvogel.model.Account;
import net.fvogel.service.SecurityService;
import net.fvogel.service.AccountService;
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
    public void register(@RequestBody Account account, HttpServletRequest request) {
        accountService.createAccount(account);
        securityService.login(account.getUserName(), account.getPassword(), request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public void login(@RequestBody Account account, HttpServletRequest request) {
        securityService.login(account, request);
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        securityService.logout(request, response);
    }

}
