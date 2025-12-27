package com.example.onlineshopping.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

   
    private String name;
    private String email;
    private Long mobileNumber;
   
    private List<AddressResponseDTO> addresses;
}
