package com.Aptech.userservice.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.userservice.Dtos.Request.RoleCreationRequest;
import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.RoleResponse;
import com.Aptech.userservice.Services.Interfaces.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/role")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleController {
    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleCreationRequest request) {
        RoleResponse role = roleService.CreateRole(request);
        return ApiResponse.<RoleResponse>builder()
                .status("SUCCESS")
                .data(role)
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.GetAllRole();
        return ApiResponse.<List<RoleResponse>>builder()
                .status("SUCCESS")
                .data(roles)
                .build();
    }

    @GetMapping("/{roleId}")
    public ApiResponse<RoleResponse> getRoleById(@PathVariable("roleId") Integer roleId) {
        RoleResponse role = roleService.GetRoleById(roleId);
        return ApiResponse.<RoleResponse>builder()
                .status("SUCCESS")
                .data(role)
                .build();
    }

    @DeleteMapping("/{roleId}")
    public ApiResponse<String> deleteRole(@PathVariable("roleId") Integer roleId) {
        roleService.DeleteRole(roleId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Role has been deleted")
                .message("Successfully deleted role with ID: " + roleId)
                .build();
    }
}
