package com.example.onlineshopping.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long userId;
    private String name;
    private String email;
    private String mobileNumber;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private List<AddressResponseDTO> addresses;
}
