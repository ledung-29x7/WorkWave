package com.Aptech.userservice.Controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.userservice.Configs.JwtTokenProvider;
import com.Aptech.userservice.Dtos.Request.JwtResponse;
import com.Aptech.userservice.Dtos.Request.LoginRequest;
import com.Aptech.userservice.Dtos.Request.PermissionCheckRequest;
import com.Aptech.userservice.Dtos.Request.RefreshTokenRequest;
import com.Aptech.userservice.Dtos.Request.RegisterRequest;
import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.UserResponse;
import com.Aptech.userservice.Entitys.Users;
import com.Aptech.userservice.Services.Interfaces.IAuthService;
import com.Aptech.userservice.Services.Interfaces.IPermissionCheckService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IPermissionCheckService service;

    private final IAuthService auth;
    private final JwtTokenProvider jwt;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest req) {
        auth.register(req);
        return ResponseEntity.ok(new ApiResponse<>("success", null, "Register success", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody LoginRequest req) throws Exception {
        return ResponseEntity.ok(new ApiResponse<>("success", auth.login(req), null, null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String uid = jwt.getUserIdFromToken(token);
        auth.logout(uid);
        jwt.blacklistToken(token);
        return ResponseEntity.ok(new ApiResponse<>("success", null, "Logout", null));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> profile(HttpServletRequest request) throws Exception {
        String uid = jwt.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        Users user = auth.getProfile(request.getHeader("Authorization").substring(7));
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .status("success")
                .data(new UserResponse(user))
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<JwtResponse>> refresh(@RequestBody RefreshTokenRequest req) throws Exception {
        return ResponseEntity.ok(new ApiResponse<>("success", auth.refresh(req.getRefreshToken()), null, null));
    }

    @PostMapping("/check-permission")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkPermission(
            @RequestBody PermissionCheckRequest req) {
        boolean result = service.checkPermission(req.getUserId(), req.getProjectId(), req.getPermissionCode());
        return ResponseEntity.ok(ApiResponse.<Map<String, Boolean>>builder()
                .status("success")
                .data(Map.of("hasPermission", result))
                .build());
    }
}
