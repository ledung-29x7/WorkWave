package com.Aptech.userservice.Dtos.Response;

import com.Aptech.userservice.Entitys.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String userId;
    private String userName;
    private String email;
    private Boolean isActive;

    public UserResponse(Users user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.isActive = user.getIsActive();
    }
}
