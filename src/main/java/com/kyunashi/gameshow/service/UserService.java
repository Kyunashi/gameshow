package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.model.User;
import com.kyunashi.gameshow.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

/**
 * service to grand utilities for accessing, changing and deleting user Data
 * uses the userrepository which handles communication with the database
 */
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

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }



    public boolean existsByUserId(int id) { return userRepository.existsByUserId(id);}


}
