package com.kyunashi.gameshow.controller;

import com.kyunashi.gameshow.dto.UserResponse;
import com.kyunashi.gameshow.model.SecurityUser;
import com.kyunashi.gameshow.model.User;
import com.kyunashi.gameshow.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

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
    public  ResponseEntity<?> getUserById(@PathVariable("userId") Integer userId, Authentication authentication) {

        SecurityUser secUser = ((SecurityUser) authentication.getPrincipal());
        int authenticatedUserId= secUser.getId();

           if(secUser.hasAuthority("ROLE_ADMIN") || userId==authenticatedUserId) {

             if(!userService.existsByUserId(userId)) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User found with id " + userId);
             }
               User wantedUser = userService.getUser(userId);
               UserResponse userResponse = new UserResponse(wantedUser.getEmail(),wantedUser.getUsername(), wantedUser.getCreated());
               return ResponseEntity.ok(userResponse);
           }
           return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }


    @PostMapping("/update/{userId}")
    public void updateUser() {

    }

}
