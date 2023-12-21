package com.kyunashi.gameshow.controller;

import com.kyunashi.gameshow.dto.LoginRequest;
import com.kyunashi.gameshow.dto.SignupRequest;
import com.kyunashi.gameshow.service.AuthService;
import com.kyunashi.gameshow.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class to manage endpoints about authentication
 * - login, creating user via signup, changing
 */
@Controller
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    //  TODO UPDATE METHODS TO ONLY RETURN HTTP STATUS CODE, MESSAGES WILL BE DONE BY FRONTEND?
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {


        if (userService.existsByUsername(signupRequest.getUsername())) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.CONFLICT);
        }
        authService.signup(signupRequest);
        return new ResponseEntity<>("Signup successful", HttpStatus.OK);


    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        boolean successful = authService.login(loginRequest, request, response);
        if (!successful)
            return new ResponseEntity<>("The Combination of password and username is incorrect", HttpStatus.CONFLICT);
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }


}
