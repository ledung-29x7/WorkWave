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
    ApiResponse<RoleResponse> CreateRole(@RequestBody RoleCreationRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.CreateRole(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> GetAllRole() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.GetAllRole())
                .build();
    }

    @GetMapping("/{roleId}")
    ApiResponse<RoleResponse> GetRoleById(@PathVariable("roleId") Integer roleId) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.GetRoleById(roleId))
                .build();
    }

    @DeleteMapping("/{roleId}")
    ApiResponse<String> DeleteRole(@PathVariable("roleId") Integer roleId) {
        roleService.DeleteRole(roleId);
        return ApiResponse.<String>builder().result("Role has been deleted").build();
    }
}
