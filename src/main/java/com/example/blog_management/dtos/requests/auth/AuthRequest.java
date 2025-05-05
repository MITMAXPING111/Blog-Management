package com.example.blog_management.dtos.requests.auth;

import lombok.*;

@Getter
@Setter
public class AuthRequest {
    private String email;
    private String password;
}
