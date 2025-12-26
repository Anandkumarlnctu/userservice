package com.example.onlineshopping.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    // Use a fixed, strong secret key for signing JWTs (should be at least 256 bits for HS256)
    private static final String SECRET = "mySuperSecretKeyForJwtSigning1234567890!@#$"; // Example, replace with a secure value in production
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    public static String generateToken(String email, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("userId", userId);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}