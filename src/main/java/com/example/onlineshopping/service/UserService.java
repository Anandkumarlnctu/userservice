package com.example.onlineshopping.service;

import com.example.onlineshopping.dto.request.*;
import com.example.onlineshopping.dto.response.*;
import com.example.onlineshopping.entity.*;
import com.example.onlineshopping.exception.*;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired EmailService emailService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(UserRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        
        if(userRepository.existsByMobileNumber(dto.getMobileNumber()))
        {
        	throw new UserAlreadyExistsException("Mobile number already exists");
        }

        String otp = generateOtp();

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setMobileNumber(dto.getMobileNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

         user.setOtp(otp);
         user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        
        user.setEmailVerified(false);

        userRepository.save(user);

        emailService.sendOtp(dto.getEmail(), otp);
        
          
    }
    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // 6 digit
    }
    public void verifyOtp(EmailVerifDTO edto) {

        User user = userRepository.findByEmail(edto.getEmail());
                

        System.out.println(user.getOtp());
        System.out.println(edto.getOtp());
        
        

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (user.getOtp() == null || user.getOtpExpiry() == null || LocalDateTime.now().isAfter(user.getOtpExpiry())) {
            throw new IllegalArgumentException("OTP expired. Please request a new one.");
        }
        if (!user.getOtp().equals(edto.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }

       
       user.setOtp(null);
       user.setOtpExpiry(null);
        user.setEmailVerified(true);
        
        userRepository.save(user);
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.isEmailVerified()) {
            throw new InvalidCredentialsException("Email or password is incorrect");
        }
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Email or password is incorrect");
        }
        // Generate JWT token with email and userId
        return JwtUtil.generateToken(user.getEmail(), user.getUserId());
    }

    public void resendOtp(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (user.isEmailVerified()) {
            throw new IllegalArgumentException("Email already verified");
        }
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);
        emailService.sendOtp(email, otp);
    }

   
}