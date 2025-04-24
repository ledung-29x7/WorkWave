package com.Aptech.projectservice.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Aptech.projectservice.Dtos.Request.EpicCreationRequest;
import com.Aptech.projectservice.Dtos.Response.ApiResponse;
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
    public ApiResponse<List<EpicResponse>> getEpics(@PathVariable("projectId") String projectId) {
        List<EpicResponse> epics = epicService.getEpicsByProject(projectId);
        return ApiResponse.<List<EpicResponse>>builder()
                .status("SUCCESS")
                .data(epics)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<EpicResponse> getEpicById(@PathVariable("id") Integer id) {
        EpicResponse epic = epicService.getEpicById(id);
        return ApiResponse.<EpicResponse>builder()
                .status("SUCCESS")
                .data(epic)
                .build();
    }

    @PostMapping("/{projectId}/project")
    public ApiResponse<String> createEpic(
            @PathVariable("projectId") String projectId,
            @RequestBody EpicCreationRequest dto) {
        epicService.createEpic(projectId, dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Epic created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateEpic(
            @PathVariable("id") Integer id,
            @RequestBody EpicCreationRequest dto) {
        epicService.updateEpic(id, dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Epic updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteEpic(@PathVariable("id") Integer id) {
        epicService.deleteEpic(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Epic deleted successfully")
                .build();
    }
}
