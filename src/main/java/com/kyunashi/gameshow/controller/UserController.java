package com.kyunashi.gameshow.controller;

import com.kyunashi.gameshow.dto.UserDto;
import com.kyunashi.gameshow.data.SecurityUser;
import com.kyunashi.gameshow.data.User;
import com.kyunashi.gameshow.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@CommonsLog
@CrossOrigin
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
        int authenticatedUserId = secUser.getId();

           if(secUser.hasAuthority("ROLE_ADMIN") || userId==authenticatedUserId) {

             if(!userService.existsByUserId(userId)) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User found with id " + userId);
             }
               User wantedUser = userService.getUser(userId);
               UserDto userDto = new UserDto(wantedUser.getEmail(),wantedUser.getUsername(), wantedUser.getPlayerName());
               return ResponseEntity.ok(userDto);
           }
           return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        SecurityUser securityUser = ((SecurityUser) authentication.getPrincipal());
        int authenticatedUserId = securityUser.getId();
        User currentUser = userService.getUser(authenticatedUserId);
        UserDto userDto = new UserDto(currentUser.getEmail(),currentUser.getUsername(), currentUser.getPlayerName());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/update/data")
    public void updateUser(@RequestBody UserDto userDto , Authentication authentication, HttpServletResponse response) {
        SecurityUser securityUser = ((SecurityUser) authentication.getPrincipal());
        int authenticatedUserId = securityUser.getId();
        boolean dataChanged = userService.updateUser(authenticatedUserId, userDto);
        if(dataChanged) {
            response.setStatus(200);
            return;
        } else {
            response.setStatus(409);
        }
    }



}
