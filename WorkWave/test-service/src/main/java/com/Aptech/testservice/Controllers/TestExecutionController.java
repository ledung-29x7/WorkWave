package com.Aptech.testservice.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.testservice.Dtos.Requests.CreateTestExecutionDTO;
import com.Aptech.testservice.Dtos.Requests.TestExecutionDTO;
import com.Aptech.testservice.Dtos.Responses.ApiResponse;
import com.Aptech.testservice.Services.Interfaces.TestExecutionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/testexecutions")
@RequiredArgsConstructor
public class TestExecutionController {
    private final TestExecutionService service;

    @PostMapping("/testcases/{testCaseId}/executions")
    public ApiResponse<String> create(@PathVariable("testCaseId") Integer testCaseId,
            @RequestBody CreateTestExecutionDTO dto) {
        dto.setTestCaseId(testCaseId);
        service.create(dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test execution created successfully")
                .build();
    }

    @GetMapping("/executions/{id}")
    public ApiResponse<TestExecutionDTO> getById(@PathVariable("id") Integer id) {
        return ApiResponse.<TestExecutionDTO>builder()
                .status("SUCCESS")
                .data(service.getById(id))
                .build();
    }

    @PutMapping("/executions/{id}")
    public ApiResponse<String> update(@PathVariable("id") Integer id,
            @RequestBody CreateTestExecutionDTO dto) {
        service.update(id, dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test execution updated successfully")
                .build();
    }

    @DeleteMapping("/executions/{id}")
    public ApiResponse<String> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test execution deleted successfully")
                .build();
    }

    @GetMapping("/testcases/{testCaseId}/executions")
    public ApiResponse<List<TestExecutionDTO>> getByTestCaseId(@PathVariable("testCaseId") Integer testCaseId) {
        return ApiResponse.<List<TestExecutionDTO>>builder()
                .status("SUCCESS")
                .data(service.getByTestCaseId(testCaseId))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<TestExecutionDTO>> search(
            @RequestParam String executedBy,
            @RequestParam Integer statusId) {
        return ApiResponse.<List<TestExecutionDTO>>builder()
                .status("SUCCESS")
                .data(service.searchTestExecutions(executedBy, statusId))
                .build();
    }
}
