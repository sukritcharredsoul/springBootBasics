package com.example.demo.security;

import com.example.demo.service.CustomDetailService;
import com.example.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class jwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService ;
    private final CustomDetailService userDetailService ;


    public jwtAuthenticationFilter(JwtService jwtService, CustomDetailService userDetailService){
        this.userDetailService = userDetailService ;
        this.jwtService = jwtService ;
    }
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization") ;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        final String jwt = authHeader.substring(7) ;
        final String username = jwtService.extractUserName(jwt) ;

        // If not authenticated and username is valid we start the process of validation ;
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            if(jwtService.isTokenValid(jwt,username)){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities()) ;

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext context = SecurityContextHolder.createEmptyContext() ;
                context.setAuthentication(token);
                SecurityContextHolder.setContext(context);
            }
        }

        // Always add this to pass on to the next filter ;
        filterChain.doFilter(request,response);

    }
}
