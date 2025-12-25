package com.example.onlineshopping.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotNull(message = "Mobile number is required")
    private Long mobileNumber;

    @NotBlank
    private String password;

    
}

