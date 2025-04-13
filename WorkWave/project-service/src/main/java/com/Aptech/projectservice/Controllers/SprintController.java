package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Aptech.projectservice.Dtos.Request.SprintRequestDto;
import com.Aptech.projectservice.Dtos.Response.SprintResponseDto;
import com.Aptech.projectservice.Services.Interfaces.SprintService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sprint")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SprintController {
    SprintService sprintService;

    @GetMapping("/{projectId}/project")
    public ResponseEntity<List<SprintResponseDto>> getSprints(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(sprintService.getSprintsByProject(projectId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintResponseDto> getSprintById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(sprintService.getSprintById(id));
    }

    @PostMapping("/{projectId}/project")
    public ResponseEntity<Void> createSprint(
            @PathVariable("projectId") String projectId,
            @RequestBody SprintRequestDto dto) {
        sprintService.createSprint(projectId, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSprint(
            @PathVariable("id") Integer id,
            @RequestBody SprintRequestDto dto) {
        sprintService.updateSprint(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprint(@PathVariable("id") Integer id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.noContent().build();
    }
}
