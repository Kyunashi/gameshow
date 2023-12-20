package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.dto.RegisterRequest;
import com.kyunashi.gameshow.model.User;
import com.kyunashi.gameshow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Transactional
    public void signup(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) return;

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode((registerRequest.getPassword())));
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
    }

}
