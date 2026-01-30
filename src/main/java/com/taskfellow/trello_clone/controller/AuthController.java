package com.taskfellow.trello_clone.controller;

import com.taskfellow.trello_clone.dto.LoginRequest;
import com.taskfellow.trello_clone.dto.RegisterRequest;
import com.taskfellow.trello_clone.entity.User;
import com.taskfellow.trello_clone.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register (@Valid @RequestBody RegisterRequest request) {
        User user = authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully: " + user.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody LoginRequest request) {
        String token  = authService.login(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }


}
