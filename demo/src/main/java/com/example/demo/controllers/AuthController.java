package com.example.demo.controllers;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final AuthService authService;
    
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
            String result = authService.registerUser(registrationDto);
            return ResponseEntity.ok(result);
    }
    
    @PostMapping("/login")
    public ResponseEntity<? extends Object> login(@Valid @RequestBody LoginDto loginDto) {
            LoginResponseDto result = authService.login(loginDto);
            return ResponseEntity.ok(result);
    }

}
