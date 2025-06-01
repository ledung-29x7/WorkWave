package com.Aptech.userservice.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.userservice.Dtos.Request.RoleRequest;
import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.RoleResponse;
import com.Aptech.userservice.Services.Interfaces.IRoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.<List<RoleResponse>>builder()
                .status("success")
                .data(roleService.getAll())
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).ROLE_CREATE)")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody RoleRequest request) {
        roleService.create(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Role created")
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).ROLE_EDIT)")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Integer id,
            @RequestBody RoleRequest request) {
        roleService.update(id, request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Role updated")
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).ROLE_DELETE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Role deleted")
                .build());
    }
}
