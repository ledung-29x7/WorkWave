package com.Aptech.releaseservice.Controllers;

import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.releaseservice.Dtos.Requests.ReleaseManagementDTO;
import com.Aptech.releaseservice.Dtos.Responses.ReleaseResponseDTO;
import com.Aptech.releaseservice.Services.Interfaces.ReleaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/release")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ReleaseController {
    ReleaseService releaseService;

    // Tạo Release mới
    @PostMapping
    public ResponseEntity<Void> createRelease(@RequestBody ReleaseManagementDTO releaseManagementDTO) {
        releaseService.createRelease(releaseManagementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Lấy Release theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ReleaseResponseDTO> getReleaseById(@PathVariable("id") Integer releaseId) {
        ReleaseResponseDTO releaseManagementDTO = releaseService.getReleaseById(releaseId);
        return ResponseEntity.ok(releaseManagementDTO);
    }

    // Cập nhật Release
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRelease(
            @PathVariable("id") Integer releaseId,
            @RequestBody ReleaseManagementDTO releaseManagementDTO) {
        releaseService.updateRelease(releaseId, releaseManagementDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Xóa Release
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelease(@PathVariable("id") Integer releaseId) {
        releaseService.deleteRelease(releaseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Lấy tất cả Releases theo ProjectId
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<ReleaseResponseDTO>> getReleasesByProjectId(
            @PathVariable("projectId") String projectId) {
        List<ReleaseResponseDTO> releases = releaseService.getReleasesByProjectId(projectId);
        return ResponseEntity.ok(releases);
    }
}
