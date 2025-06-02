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

import com.Aptech.userservice.Configs.JwtTokenProvider;
import com.Aptech.userservice.Dtos.Request.ProjectRequest;
import com.Aptech.userservice.Dtos.Request.ProjectUpdateRequest;
import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.ProjectResponse;
import com.Aptech.userservice.Services.Interfaces.IProjectMemberService;
import com.Aptech.userservice.Services.Interfaces.IProjectService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final IProjectService projectService;
    private final JwtTokenProvider jwt;
    private final IProjectMemberService projectRoleAssignmentService;

    @PostMapping()
    public ResponseEntity<ApiResponse<ProjectResponse>> create(@RequestBody ProjectRequest request,
            HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // ✅ Tạo project
        ProjectResponse project = projectService.create(request, userId);

        // ✅ Tự động gán người tạo vào project với role Admin (RoleId = 1)
        projectRoleAssignmentService.assignUser(project.getProjectId(), userId, 1); // roleId = 1 là Admin

        return ResponseEntity.ok(ApiResponse.<ProjectResponse>builder()
                .status("success")
                .data(project)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllByUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // jwt là JwtTokenUtil hoặc class bạn đã inject

        List<ProjectResponse> projects = projectService.getAllByUser(userId);
        return ResponseEntity.ok(ApiResponse.<List<ProjectResponse>>builder()
                .status("success")
                .data(projects)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.<ProjectResponse>builder()
                .status("success")
                .data(projectService.getById(id))
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).PROJECT_EDIT)")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable String id, @RequestBody ProjectUpdateRequest req) {
        projectService.update(id, req);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Project updated successfully")
                .build());
    }

    @PreAuthorize("hasAuthority(T(com.Aptech.userservice.enums.PermissionCode).PROJECT_DELETE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        projectService.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("success")
                .message("Project deleted successfully")
                .build());
    }
}
