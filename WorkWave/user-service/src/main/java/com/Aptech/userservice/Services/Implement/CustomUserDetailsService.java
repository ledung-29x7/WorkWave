package com.Aptech.userservice.Services.Implement;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Aptech.userservice.Configs.CustomUserDetails;
import com.Aptech.userservice.Entitys.Users;
import com.Aptech.userservice.Repositorys.PermissionRepo;
import com.Aptech.userservice.Repositorys.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserRepository userRepo;
    private final EntityManager em;
    private final PermissionRepo permissionRepository;

    /**
     * Load User + quyền từ Project cụ thể
     */
    public UserDetails loadUserByIdAndProject(String userId, String projectId) {
        // 1. Lấy thông tin User
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 2. Lấy danh sách quyền từ bảng Permission (qua Role)
        List<String> permissionCodes = permissionRepository.getPermissionCodesByUserAndProject(userId, projectId);

        // 3. Trả về CustomUserDetails chứa quyền
        return new CustomUserDetails(user, permissionCodes);
    }

    public UserDetails loadUserGlobalAuthorities(String userId) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Lấy danh sách quyền toàn cục
        List<String> permissionCodes = permissionRepository.getGlobalPermissionCodesByUserId(userId);
        System.out.println("🎯 Permissions returned: " + permissionCodes);

        return new CustomUserDetails(user, permissionCodes);
    }

}
