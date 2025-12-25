package com.example.onlineshopping.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class EmailVerifDTO {

	private String email;
	private String otp;
	
}
