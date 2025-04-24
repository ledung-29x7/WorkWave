package com.Aptech.testservice.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;
import com.Aptech.testservice.Dtos.Responses.ApiResponse;
import com.Aptech.testservice.Services.Interfaces.TestCaseService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/testcases")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class TestCaseController {
    private final TestCaseService service;

    @PostMapping
    public ApiResponse<String> createTestCase(@RequestBody TestCaseDTO dto) {
        service.createTestCase(dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test case created successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TestCaseDTO> getById(@PathVariable("id") Integer id) {
        return ApiResponse.<TestCaseDTO>builder()
                .status("SUCCESS")
                .data(service.getTestCaseById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateTestCase(@PathVariable("id") Integer id, @RequestBody TestCaseDTO dto) {
        service.updateTestCase(id, dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test case updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTestCase(@PathVariable("id") Integer id) {
        service.deleteTestCase(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test case deleted successfully")
                .build();
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<TestCaseDTO>> getByProject(@PathVariable("projectId") String projectId) {
        return ApiResponse.<List<TestCaseDTO>>builder()
                .status("SUCCESS")
                .data(service.getTestCasesByProject(projectId))
                .build();
    }
}
