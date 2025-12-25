package com.example.onlineshopping.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

    private Long addressId;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
