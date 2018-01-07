package net.fvogel.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fvogel.model.User;
import net.fvogel.service.SecurityService;
import net.fvogel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public void register(@RequestBody User user, HttpServletRequest request) {
        userService.registerUser(user);
        securityService.login(user.getName(), user.getPassword(), request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public void login(@RequestBody User user, HttpServletRequest request) {
        securityService.login(user, request);
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        securityService.logout(request, response);
    }

}
