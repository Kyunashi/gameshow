package com.kyunashi.gameshow.controller;

import com.kyunashi.gameshow.model.User;
import com.kyunashi.gameshow.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to manage Endpoints regarding the user
 * - all returns for admins  list of all in teh database registerd users
 * - userId to show the logged in user their own profile data
 * - update for users to change name, password, email etc
 */
@Controller
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {


    private final UserService userService;



    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }


    @PostMapping("/{userId}/update")
    public void updateUser() {

    }

}
