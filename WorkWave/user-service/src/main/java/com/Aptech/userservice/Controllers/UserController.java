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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.userservice.Dtos.Request.UserRequest;
import com.Aptech.userservice.Dtos.Request.UserUpdateRequest;
import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.PagedUserResponse;
import com.Aptech.userservice.Dtos.Response.UserResponse;
import com.Aptech.userservice.Services.Interfaces.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

        private final IUserService userService;

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).USER_CREATE)")
        @PostMapping
        public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserRequest request) {
                UserResponse created = userService.createUser(request);
                return ResponseEntity.ok(
                                ApiResponse.<UserResponse>builder()
                                                .status("success")
                                                .data(created)
                                                .build());
        }

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).USER_VIEW)")
        @GetMapping
        public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
                List<UserResponse> list = userService.getAllUsers();
                return ResponseEntity.ok(
                                ApiResponse.<List<UserResponse>>builder()
                                                .status("success")
                                                .data(list)
                                                .build());
        }

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).USER_VIEW)")
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable("id") String id) {
                UserResponse user = userService.getUserById(id);
                return ResponseEntity.ok(
                                ApiResponse.<UserResponse>builder()
                                                .status("success")
                                                .data(user)
                                                .build());
        }

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).USER_EDIT)")
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> updateUser(
                        @PathVariable("id") String id,
                        @RequestBody UserUpdateRequest request) {
                userService.updateUser(id, request);
                return ResponseEntity.ok(
                                ApiResponse.<Void>builder()
                                                .status("success")
                                                .message("User updated successfully")
                                                .build());
        }

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).USER_DELETE)")
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") String id) {
                userService.deleteUser(id);
                return ResponseEntity.ok(
                                ApiResponse.<Void>builder()
                                                .status("success")
                                                .message("User deleted successfully")
                                                .build());
        }

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).USER_SEARCH)")
        @GetMapping("/search")
        public ResponseEntity<ApiResponse<List<UserResponse>>> searchUser(@RequestParam("q") String keyword) {
                List<UserResponse> results = userService.searchUsers(keyword);
                return ResponseEntity.ok(
                                ApiResponse.<List<UserResponse>>builder()
                                                .status("success")
                                                .data(results)
                                                .build());
        }

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).USER_SEARCH)")
        @GetMapping("/search-paged")
        public ResponseEntity<ApiResponse<PagedUserResponse>> searchUsersPaged(
                        @RequestParam String q,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size) {

                PagedUserResponse response = userService.searchUsersPaged(q, page, size);
                return ResponseEntity.ok(ApiResponse.<PagedUserResponse>builder()
                                .status("success")
                                .data(response)
                                .build());
        }

}
