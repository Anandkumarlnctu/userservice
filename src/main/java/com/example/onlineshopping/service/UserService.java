package com.example.onlineshopping.service;

import com.example.onlineshopping.dto.request.*;
import com.example.onlineshopping.dto.response.*;
import com.example.onlineshopping.entity.*;
import com.example.onlineshopping.exception.*;
import com.example.onlineshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired EmailService emailService;

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
        user.setPassword(dto.getPassword());

         user.setOtp(otp);
        
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
        
        

        if (!user.getOtp().equals(edto.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }

       
       user.setOtp(null);
        user.setEmailVerified(true);
        
        userRepository.save(user);
    }


   

   
}
