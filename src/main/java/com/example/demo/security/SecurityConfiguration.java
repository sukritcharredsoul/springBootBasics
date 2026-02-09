package com.example.demo.security;

import com.example.demo.service.CustomDetailService;
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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomDetailService userDetailsService;
    private final jwtAuthenticationFilter jwtAuthenticationFilter ;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint ;
    private final CustomAccessDeniedHandler customAccessDeniedHandler ;

    public SecurityConfiguration(CustomDetailService userDetailsService, jwtAuthenticationFilter jwtAuthenticationFilter , CustomAccessDeniedHandler customAccessDeniedHandler, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter ;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint ;
        this.customAccessDeniedHandler = customAccessDeniedHandler ;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
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
