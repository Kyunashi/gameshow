package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.LoginRequest;
import com.kyunashi.gameshow.dto.SignupRequest;
import com.kyunashi.gameshow.data.User;
import com.kyunashi.gameshow.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service class which is used by the controller class to handle the actual tasks and return the according data if needed
 * It access the user Repository to get user information from database
 * handles authentication (login  / registration) by using the authentication manager from the SecurityConfig
 * handles sessions to persist authentication by setting the according SecurityContext
 */
@Service
@AllArgsConstructor
@CommonsLog
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository;

    @Transactional
    public boolean signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return false;
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode((signupRequest.getPassword())));
        user.setEmail(signupRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(true);
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return true;
    }


    public void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        // will return error 403 forbidden here if wrong credentials
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        log.info("LOGGED IN WITH AUTHORITIES: " + authentication.getAuthorities());

    }
}
