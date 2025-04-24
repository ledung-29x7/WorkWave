package com.Aptech.bugtrackingservice.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.bugtrackingservice.Dtos.Requests.CreateBugRequest;
import com.Aptech.bugtrackingservice.Dtos.Requests.UpdateBugRequestDTO;
import com.Aptech.bugtrackingservice.Dtos.Responses.ApiResponse;
import com.Aptech.bugtrackingservice.Dtos.Responses.BugDetailsDTO;
import com.Aptech.bugtrackingservice.Services.Interfaces.BugService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bug")
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;

    @PostMapping
    public ApiResponse<String> createBug(@RequestBody CreateBugRequest request) {
        bugService.createBug(request);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Bug created successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBug(@PathVariable("id") Integer id) {
        bugService.deleteBug(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Bug deleted successfully")
                .build();
    }

    @GetMapping("/projects/{projectId}/bugs")
    public ApiResponse<List<BugDetailsDTO>> getBugsByProject(@PathVariable("projectId") String projectId) {
        return ApiResponse.<List<BugDetailsDTO>>builder()
                .status("SUCCESS")
                .data(bugService.getBugsByProject(projectId))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BugDetailsDTO> getBugById(@PathVariable("id") Integer id) {
        return ApiResponse.<BugDetailsDTO>builder()
                .status("SUCCESS")
                .data(bugService.getBugDetailsById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateBug(
            @PathVariable("id") Integer id,
            @RequestBody UpdateBugRequestDTO request) {
        request.setBugId(id);
        bugService.updateBugWithHistory(request);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Bug updated successfully")
                .build();
    }

}
