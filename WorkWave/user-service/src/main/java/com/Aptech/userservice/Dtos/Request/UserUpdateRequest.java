package com.Aptech.userservice.Dtos.Request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String userName;
    private String email;
}
