package com.taskfellow.trello_clone.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
