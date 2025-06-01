package com.Aptech.userservice.Services.Interfaces;

import com.Aptech.userservice.Dtos.Request.JwtResponse;
import com.Aptech.userservice.Dtos.Request.LoginRequest;
import com.Aptech.userservice.Dtos.Request.RegisterRequest;
import com.Aptech.userservice.Entitys.Users;

public interface IAuthService {
    public void register(RegisterRequest req);

    public JwtResponse login(LoginRequest req) throws Exception;

    public JwtResponse refresh(String token) throws Exception;

    public void logout(String userId);

    public Users getProfile(String token) throws Exception;
}
