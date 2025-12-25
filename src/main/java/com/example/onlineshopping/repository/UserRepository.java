package com.example.onlineshopping.repository;

import com.example.onlineshopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);
}

