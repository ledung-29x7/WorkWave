package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Aptech.projectservice.Dtos.Request.EpicCreationRequest;
import com.Aptech.projectservice.Dtos.Response.EpicResponse;
import com.Aptech.projectservice.Services.Interfaces.EpicService;

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

    @GetMapping("/{projectId}/project")
    public ResponseEntity<List<EpicResponse>> getEpics(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(epicService.getEpicsByProject(projectId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpicResponse> getEpicById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(epicService.getEpicById(id));
    }

    @PostMapping("/{projectId}/project")
    public ResponseEntity<Void> createEpic(
            @PathVariable("projectId") String projectId,
            @RequestBody EpicCreationRequest dto) {
        epicService.createEpic(projectId, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEpic(
            @PathVariable("id") Integer id,
            @RequestBody EpicCreationRequest dto) {
        epicService.updateEpic(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpic(@PathVariable("id") Integer id) {
        epicService.deleteEpic(id);
        return ResponseEntity.noContent().build();
    }
}
