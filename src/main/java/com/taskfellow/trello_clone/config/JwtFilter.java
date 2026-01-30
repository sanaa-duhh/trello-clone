package com.taskfellow.trello_clone.config;

import com.taskfellow.trello_clone.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // LOGGING START
        System.out.println("Processing Request: " + request.getRequestURI());
        System.out.println("Auth Header: " + authHeader);
        // LOGGING END

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Print the token the server sees
            System.out.println("Extracted Token: " + token.substring(0, 10) + "...");

            if (jwtUtil.validateToken(token)) {
                System.out.println("Token Validated Successfully!"); // <--- We want to see this

                String email = jwtUtil.extractEmail(token);
                UserDetails userDetails = new User(email, "", Collections.emptyList());
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("Token Validation FAILED!"); // <--- If you see this, the Key is wrong
            }
        } else {
            System.out.println("No Bearer Header found.");
        }

        chain.doFilter(request, response);
    }
}


