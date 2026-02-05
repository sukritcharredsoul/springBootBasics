package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository ;

@Service
public class CustomerDetailService implements UserDetailsService {

    private final UserRepository userRepository ;

    public CustomerDetailService(UserRepository userRepository){
        this.userRepository = userRepository ;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("Username Not found")) ;
        return org.springframework.security.core.userdetails.User.builder().username(user.getName()).password(user.getPassword()).roles(user.getRole()).build() ;
    }
}
