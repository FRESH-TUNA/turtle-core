package com.remember.core.authentication.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
