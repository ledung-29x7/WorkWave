package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.Aptech.projectservice.Configs.JwtTokenProvider;
import com.Aptech.projectservice.Configs.ProjectContext;
import com.Aptech.projectservice.Dtos.Request.EpicCreationRequest;
import com.Aptech.projectservice.Dtos.Response.ApiResponse;
import com.Aptech.projectservice.Dtos.Response.EpicResponse;
import com.Aptech.projectservice.Services.Interfaces.EpicService;
import com.Aptech.projectservice.annotation.PermissionRequired;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/epic")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class EpicController {
    EpicService epicService;
    JwtTokenProvider jwt;

    @PreAuthorize("hasAuthority('EPIC_VIEW')")
    @GetMapping()
    public ApiResponse<List<EpicResponse>> getEpics() {
        String projectId = ProjectContext.getProjectId();
        List<EpicResponse> epics = epicService.getEpicsByProject(projectId);
        return ApiResponse.<List<EpicResponse>>builder()
                .status("SUCCESS")
                .data(epics)
                .build();
    }

    @PreAuthorize("hasAuthority('EPIC_VIEW')")
    @GetMapping("/{id}")
    public ApiResponse<EpicResponse> getEpicById(@PathVariable("id") Integer id) {
        EpicResponse epic = epicService.getEpicById(id);
        return ApiResponse.<EpicResponse>builder()
                .status("SUCCESS")
                .data(epic)
                .build();
    }

    @PermissionRequired("EPIC_CREATE")
    @PostMapping()
    public ApiResponse<String> createEpic(
            @RequestBody EpicCreationRequest dto, HttpServletRequest http) {
        String projectId = ProjectContext.getProjectId();
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        epicService.createEpic(projectId, dto, userId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Epic created successfully")
                .build();
    }

    @PermissionRequired("EPIC_UPDATE")
    @PutMapping("/{id}")
    public ApiResponse<String> updateEpic(
            @PathVariable("id") Integer id,
            @RequestBody EpicCreationRequest dto, HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        epicService.updateEpic(id, dto, userId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Epic updated successfully")
                .build();
    }

    @PermissionRequired("EPIC_DELETE")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteEpic(@PathVariable("id") Integer id) {
        epicService.deleteEpic(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Epic deleted successfully")
                .build();
    }
}
