package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.LoginRequest;
import com.kyunashi.gameshow.dto.RegisterRequest;
import com.kyunashi.gameshow.model.User;
import com.kyunashi.gameshow.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public boolean signup(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) return false;

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode((registerRequest.getPassword())));
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        return true;
    }


    public boolean login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        //TODO print authorities, if authenticated is true here, why is it not stored anywhere?
        if (authenticate.isAuthenticated()) return true;
        return false;
    }
}
