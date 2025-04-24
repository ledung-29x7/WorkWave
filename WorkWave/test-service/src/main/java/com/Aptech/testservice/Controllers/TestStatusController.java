package com.Aptech.testservice.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.testservice.Dtos.Requests.TestStatusDTO;
import com.Aptech.testservice.Dtos.Responses.ApiResponse;
import com.Aptech.testservice.Services.Interfaces.TestStatusService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teststatus")
@RequiredArgsConstructor
public class TestStatusController {
    private final TestStatusService service;

    @GetMapping
    public ApiResponse<List<TestStatusDTO>> getAll() {
        return ApiResponse.<List<TestStatusDTO>>builder()
                .status("SUCCESS")
                .data(service.getAllStatuses())
                .build();
    }

    @PostMapping
    public ApiResponse<String> create(@RequestBody TestStatusDTO dto) {
        service.create(dto);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test status created successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Test status deleted successfully")
                .build();
    }
}
