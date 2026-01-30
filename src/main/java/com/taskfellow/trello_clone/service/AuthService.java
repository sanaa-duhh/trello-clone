package com.taskfellow.trello_clone.service;

import com.taskfellow.trello_clone.dto.RegisterRequest;
import com.taskfellow.trello_clone.entity.User;
import com.taskfellow.trello_clone.repository.UserRepository;
import com.taskfellow.trello_clone.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRole("USER");

        //first encrypt
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);


    }

    public String login(String email, String passsword){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(passsword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(email);
    }
}

