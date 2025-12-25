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

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String password;

    @NotEmpty
    private List<AddressRequestDTO> addresses;
}

