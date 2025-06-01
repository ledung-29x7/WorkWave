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

import com.Aptech.userservice.Dtos.Request.PermissionRequest;
import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.PermissionResponse;
import com.Aptech.userservice.Services.Interfaces.IPermissionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final IPermissionService permissionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.<List<PermissionResponse>>builder()
                .status("success")
                .data(permissionService.getAll())
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).PERMISSION_CREATE)")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody PermissionRequest request) {
        permissionService.create(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Permission created")
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).PERMISSION_EDIT)")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Integer id,
            @RequestBody PermissionRequest request) {
        permissionService.update(id, request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Permission updated")
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).PERMISSION_DELETE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        permissionService.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Permission deleted")
                .build());
    }
}
