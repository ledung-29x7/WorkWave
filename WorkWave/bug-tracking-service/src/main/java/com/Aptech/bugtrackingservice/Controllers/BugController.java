package com.Aptech.bugtrackingservice.Controllers;

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

import com.Aptech.bugtrackingservice.Configs.JwtTokenProvider;
import com.Aptech.bugtrackingservice.Dtos.Requests.CreateBugRequest;
import com.Aptech.bugtrackingservice.Dtos.Requests.UpdateBugRequestDTO;
import com.Aptech.bugtrackingservice.Dtos.Responses.ApiResponse;
import com.Aptech.bugtrackingservice.Dtos.Responses.BugDetailsDTO;
import com.Aptech.bugtrackingservice.Services.Interfaces.BugService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bug")
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;
    private final JwtTokenProvider jwt;

    @PreAuthorize("hasAuthority('BUG_CREATE')")
    @PostMapping
    public ApiResponse<String> createBug(@RequestBody CreateBugRequest request, HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);
        String userId = null;
        try {
            userId = jwt.getUserIdFromToken(token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        bugService.createBug(request, userId);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Bug created successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('BUG_DELETE')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBug(@PathVariable("id") Integer id) {
        bugService.deleteBug(id);
        return ApiResponse.<String>builder()
                .status("SUCCESS")
                .data("Bug deleted successfully")
                .build();
    }

    @PreAuthorize("hasAuthority('BUG_VIEW')")
    @GetMapping("/projects/{projectId}/bugs")
    public ApiResponse<List<BugDetailsDTO>> getBugsByProject(@PathVariable("projectId") String projectId) {
        return ApiResponse.<List<BugDetailsDTO>>builder()
                .status("SUCCESS")
                .data(bugService.getBugsByProject(projectId))
                .build();
    }

    @PreAuthorize("hasAuthority('BUG_VIEW')")
    @GetMapping("/{id}")
    public ApiResponse<BugDetailsDTO> getBugById(@PathVariable("id") Integer id) {
        return ApiResponse.<BugDetailsDTO>builder()
                .status("SUCCESS")
                .data(bugService.getBugDetailsById(id))
                .build();
    }

    @PreAuthorize("hasAuthority('BUG_UPDATE')")
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
