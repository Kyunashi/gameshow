package com.kyunashi.gameshow.service;

import com.kyunashi.gameshow.model.User;
import com.kyunashi.gameshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUser(int userId) {
        return userRepository.findById(userId).get();
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }



}
