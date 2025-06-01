package com.Aptech.testservice.Controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.testservice.Configs.JwtTokenProvider;
import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;
import com.Aptech.testservice.Dtos.Responses.ApiResponse;
import com.Aptech.testservice.Dtos.Responses.TestCaseResponse;
import com.Aptech.testservice.Services.Interfaces.TestCaseService;

import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtTokenProvider jwt;

    @PreAuthorize("hasAuthority('TESTCASE_CREATE')")
    @PostMapping
    public ApiResponse<String> createTestCase(@RequestBody TestCaseDTO dto, HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        service.createTestCase(dto, userId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test case created successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('TESTCASE_VIEW')")
    @GetMapping("/{id}")
    public ApiResponse<TestCaseResponse> getById(@PathVariable("id") Integer id) {
        return ApiResponse.<TestCaseResponse>builder()
                .status("SUCCESS")
                .data(service.getTestCaseById(id))
                .build();
    }

    @PreAuthorize("hasAuthority('TESTCASE_UPDATE')")
    @PutMapping("/{id}")
    public ApiResponse<String> updateTestCase(@PathVariable("id") Integer id, @RequestBody TestCaseDTO dto) {
        service.updateTestCase(id, dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test case updated successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('TESTCASE_DELETE')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTestCase(@PathVariable("id") Integer id) {
        service.deleteTestCase(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test case deleted successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('TESTCASE_VIEW')")
    @GetMapping("/project/{projectId}")
    public ApiResponse<List<TestCaseResponse>> getByProject(@PathVariable("projectId") String projectId) {
        return ApiResponse.<List<TestCaseResponse>>builder()
                .status("SUCCESS")
                .data(service.getTestCasesByProject(projectId))
                .build();
    }
}
