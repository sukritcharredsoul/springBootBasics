package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.Register;
import com.example.demo.service.UserService;
import com.example.demo.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService ;
    private final JwtService JwtService;
    private final AuthenticationManager authenticationManager ;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.JwtService = jwtService ;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Register request){
        userService.register(request) ;
        return ResponseEntity.status(HttpStatus.CREATED).body("User Created") ;

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(request.getName(),request.getPassword())) ;
       String token = JwtService.generateToken(request.getName());
        return ResponseEntity.ok(token) ;
    }


}
