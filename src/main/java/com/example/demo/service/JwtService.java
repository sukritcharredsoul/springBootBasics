package com.example.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    public final String SECRETSTRING = "mYHAklkafajkdfnuiehajdnakduhfauisif9349823rhnaksjdfajsklfL" ;

    public String generateToken(String name){
        return Jwts.builder().setSubject(name).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 )).
                signWith(Keys.hmacShaKeyFor(SECRETSTRING.getBytes()), SignatureAlgorithm.HS256).compact() ;

    }



}
