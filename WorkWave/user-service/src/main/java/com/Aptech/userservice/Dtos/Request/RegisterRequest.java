package com.Aptech.userservice.Dtos.Request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String password;
}
