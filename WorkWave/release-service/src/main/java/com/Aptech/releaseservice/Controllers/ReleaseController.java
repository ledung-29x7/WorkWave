package com.Aptech.releaseservice.Controllers;

import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.releaseservice.Configs.JwtTokenProvider;
import com.Aptech.releaseservice.Dtos.Requests.ReleaseManagementDTO;
import com.Aptech.releaseservice.Dtos.Responses.ApiResponse;
import com.Aptech.releaseservice.Dtos.Responses.ReleaseResponseDTO;
import com.Aptech.releaseservice.Services.Interfaces.ReleaseService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/release")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ReleaseController {
    ReleaseService releaseService;
    JwtTokenProvider jwt;

    @PostMapping
    public ApiResponse<String> createRelease(@RequestBody ReleaseManagementDTO releaseManagementDTO,
            HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        releaseService.createRelease(releaseManagementDTO, userId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Release created successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ReleaseResponseDTO> getReleaseById(@PathVariable("id") Integer releaseId) {
        ReleaseResponseDTO dto = releaseService.getReleaseById(releaseId);
        return ApiResponse.<ReleaseResponseDTO>builder()
                .status("SUCCESS")
                .data(dto)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateRelease(
            @PathVariable("id") Integer releaseId,
            @RequestBody ReleaseManagementDTO releaseManagementDTO, HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        releaseService.updateRelease(releaseId, releaseManagementDTO, userId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Release updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteRelease(@PathVariable("id") Integer releaseId) {
        releaseService.deleteRelease(releaseId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Release deleted successfully")
                .build();
    }

    @GetMapping("/projects/{projectId}")
    public ApiResponse<List<ReleaseResponseDTO>> getReleasesByProjectId(@PathVariable("projectId") String projectId) {
        List<ReleaseResponseDTO> releases = releaseService.getReleasesByProjectId(projectId);
        return ApiResponse.<List<ReleaseResponseDTO>>builder()
                .status("SUCCESS")
                .data(releases)
                .build();
    }
}
