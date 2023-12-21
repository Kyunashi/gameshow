package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.model.User;
import com.kyunashi.gameshow.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@CommonsLog
public class UserService {

    private final UserRepository userRepository;


    public User getUser(int userId) {
        return userRepository.findById(userId).get();
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }



}
