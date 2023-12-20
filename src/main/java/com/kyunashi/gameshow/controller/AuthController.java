package com.kyunashi.gameshow.controller;

import com.kyunashi.gameshow.dto.LoginRequest;
import com.kyunashi.gameshow.dto.RegisterRequest;
import com.kyunashi.gameshow.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {

        boolean successful = authService.signup(registerRequest);

        if (!successful) return new ResponseEntity<>("Username is already taken", HttpStatus.CONFLICT);

        return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean successful = authService.login(loginRequest);
        if(!successful) return new ResponseEntity<>("The Combination of password and username is incorrect", HttpStatus.CONFLICT);
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

}
