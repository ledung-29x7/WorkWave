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

import com.Aptech.userservice.Configs.ProjectContext;
import com.Aptech.userservice.Dtos.Request.ProjectMemberRequest;
import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.ProjectMemberResponse;
import com.Aptech.userservice.Services.Interfaces.IProjectMemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class ProjectMemberController {

        private final IProjectMemberService service;

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).PROJECT_VIEW_MEMBER)")
        @GetMapping
        public ResponseEntity<ApiResponse<List<ProjectMemberResponse>>> getMembers() {
                String projectId = ProjectContext.getProjectId();
                return ResponseEntity.ok(ApiResponse.<List<ProjectMemberResponse>>builder()
                                .status("success")
                                .data(service.getMembers(projectId))
                                .build());
        }

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).PROJECT_ASSIGN_MEMBER)")
        @PostMapping
        public ResponseEntity<ApiResponse<Void>> assign(
                        @RequestBody ProjectMemberRequest request) {
                String projectId = ProjectContext.getProjectId();
                service.assignUser(projectId, request);
                return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("success")
                                .message("User assigned to project")
                                .build());
        }

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).PROJECT_UPDATE_ROLE)")
        @PutMapping("/{userId}")
        public ResponseEntity<ApiResponse<Void>> updateRole(
                        @PathVariable String userId,
                        @RequestParam Integer roleId) {
                String projectId = ProjectContext.getProjectId();
                service.updateRole(projectId, userId, roleId);
                return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("success")
                                .message("User role updated")
                                .build());
        }

        @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).PROJECT_REMOVE_MEMBER)")
        @DeleteMapping("/{userId}")
        public ResponseEntity<ApiResponse<Void>> remove(
                        @PathVariable String userId) {
                String projectId = ProjectContext.getProjectId();
                service.removeUser(projectId, userId);
                return ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("success")
                                .message("User removed from project")
                                .build());
        }
}
