package com.example.demo.service;


import com.example.demo.dto.Register;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder ;
    }

    public User register(Register request){

        String encodedPass = passwordEncoder.encode(request.getPassword()) ;

        User user = new User(request.getName(),request.getAge(),encodedPass,"User") ;

        return userRepository.save(user) ;

    }
}
