package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("{jwt.secret}")
    private String SecretKey ;

    public String generateToken(String name){
        SecretKey key = Keys.hmacShaKeyFor(SecretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().subject(name).issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .signWith(key)
                .compact() ;
    }

    public String extractUserName(String token){
        return extractAllClaims(token).getSubject() ;
    }

    private boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date()) ;
    }

    public boolean isTokenValid(String token , String username){
        final String extractedUsername = extractUserName(token) ;
        return extractedUsername.equals(username) && !isTokenExpired(token) ;
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SecretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload() ;
    }

}
