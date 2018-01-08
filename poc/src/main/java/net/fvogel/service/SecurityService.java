package net.fvogel.service;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fvogel.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    public String getCurrentSessionUserName() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof UserDetails) {
            return ((UserDetails) details).getUsername();
        }
        return null;
    }

    public void login(Login login, HttpServletRequest request) {
        // TODO: add null check
        login(login.getLoginName(), login.getPassword(), request);
    }

    public void login(String loginName, String password, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginName);
        if (userDetails != null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, new HashSet<>());
            authenticationManager.authenticate(token);

            if (token.isAuthenticated()) {
                // Create session & JSESSIONID Cookie, as Spring Security's
                // default setting is "create session if required"
                request.getSession(true);

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(token);
                return;
            }
            System.out.println("Not authenticated");
            return;
        }
        System.out.println("No user named '" + loginName + "' could be found");
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

}
