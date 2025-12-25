package com.example.onlineshopping.service;

import com.example.onlineshopping.dto.request.*;
import com.example.onlineshopping.dto.response.*;
import com.example.onlineshopping.entity.*;
import com.example.onlineshopping.exception.*;
import com.example.onlineshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO register(UserRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setMobileNumber(dto.getMobileNumber());
        user.setPassword(dto.getPassword()); // encrypt later
        user.setIsActive(true);

        user.setAddresses(
                dto.getAddresses().stream()
                        .map(a -> new Address(
                                null,
                                a.getAddressLine(),
                                a.getCity(),
                                a.getState(),
                                a.getCountry(),
                                a.getPincode()
                        ))
                        .collect(Collectors.toList())
        );

        return mapToResponse(userRepository.save(user));
    }

    public UserResponseDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found: " + id));

        return mapToResponse(user);
    }

    private UserResponseDTO mapToResponse(User user) {

        return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getIsActive(),
                user.getCreatedAt(),
                user.getAddresses().stream()
                        .map(a -> new AddressResponseDTO(
                                a.getAddressId(),
                                a.getAddressLine(),
                                a.getCity(),
                                a.getState(),
                                a.getCountry(),
                                a.getPincode()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
