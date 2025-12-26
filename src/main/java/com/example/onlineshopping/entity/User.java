package com.example.onlineshopping.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private Long mobileNumber;

    private String password;
    private boolean emailVerified;
    private String otp;

    private LocalDateTime otpExpiry;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") // unidirectional
    private List<Address> addresses;

}