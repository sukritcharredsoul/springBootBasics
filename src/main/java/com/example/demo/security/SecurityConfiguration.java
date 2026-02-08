package com.example.demo.security;

import com.example.demo.service.CustomerDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomerDetailService userDetailsService;
    private final jwtAuthenticationFilter jwtAuthenticationFilter ;

    public SecurityConfiguration(CustomerDetailService userDetailsService, jwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter ;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**")
                        .permitAll()
                        .anyRequest().authenticated())


                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) ;

        return http.build() ;
    }


    /*
    * Password encoder method which returns BcryptPasswordEncoder() ;
    **/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder() ;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration ) throws Exception {
        return configuration.getAuthenticationManager() ;
    }


}
