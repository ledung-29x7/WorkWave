package com.Aptech.userservice.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.RolePermissionResponse;
import com.Aptech.userservice.Services.Interfaces.IRolePermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles/{roleId}/permissions")
@RequiredArgsConstructor
public class RolePermissionController {

    private final IRolePermissionService service;

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).ROLE_VIEW_PERMISSION)")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RolePermissionResponse>>> getByRole(@PathVariable Integer roleId) {
        return ResponseEntity.ok(ApiResponse.<List<RolePermissionResponse>>builder()
                .status("success")
                .data(service.getPermissionsByRole(roleId))
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).ROLE_ASSIGN_PERMISSION)")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> add(@PathVariable Integer roleId,
            @RequestParam Integer permissionId) {
        service.addPermission(roleId, permissionId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Permission added to role")
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).ROLE_REMOVE_PERMISSION)")
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ApiResponse<Void>> remove(@PathVariable Integer roleId,
            @PathVariable Integer permissionId) {
        service.removePermission(roleId, permissionId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Permission removed from role")
                .build());
    }
}
