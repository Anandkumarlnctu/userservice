package com.example.onlineshopping.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET =
            "mySuperSecretKeyForJwtSigning1234567890!@#$myKey";

    private static final long EXPIRATION_TIME = 86400000; // 1 day

    private static final SecretKey SIGNING_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String generateToken(String email, Long userId) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("userId", userId);

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public static Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }

    public static boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SIGNING_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
