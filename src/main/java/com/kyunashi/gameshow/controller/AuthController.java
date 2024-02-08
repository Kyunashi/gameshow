package com.kyunashi.gameshow.controller;

import com.kyunashi.gameshow.dto.LoginRequest;
import com.kyunashi.gameshow.dto.SignupRequest;
import com.kyunashi.gameshow.service.AuthService;
import com.kyunashi.gameshow.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to manage endpoints about authentication
 * - login, creating user via signup, changing
 */
@Controller
@CrossOrigin
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest signupRequest, HttpServletResponse response) {


        if (userService.existsByUsername(signupRequest.getUsername())) {
            response.setStatus(409);
        }
        authService.signup(signupRequest);
        response.setStatus(200);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        boolean successful = authService.login(loginRequest, request, response);
        if (!successful) {
            response.setStatus(409);
        }
        response.setStatus(200);
    }


}
