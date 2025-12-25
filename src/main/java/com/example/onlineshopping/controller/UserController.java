package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.EmailVerifDTO;
import com.example.onlineshopping.dto.request.UserRequestDTO;
import com.example.onlineshopping.dto.response.UserResponseDTO;
import com.example.onlineshopping.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody UserRequestDTO dto) {

    	userService.register(dto);
        return ResponseEntity.ok("OTP sent on email ");
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody EmailVerifDTO edto) {
        userService.verifyOtp(edto);
        return ResponseEntity.ok("Registration successful");
    }


   
}
