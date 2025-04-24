package com.Aptech.userservice.Controllers;

import com.Aptech.userservice.Dtos.Request.TeamCreationRequest;
import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.TeamResponse;
import com.Aptech.userservice.Services.Interfaces.TeamService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TeamController {
    TeamService teamService;

    @PostMapping
    public ApiResponse<TeamResponse> createTeam(@RequestBody TeamCreationRequest request) {
        TeamResponse response = teamService.CreateTeam(request);
        return ApiResponse.<TeamResponse>builder()
                .status("SUCCESS")
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<TeamResponse>> getAllTeams() {
        List<TeamResponse> teams = teamService.GetAllTeam();
        return ApiResponse.<List<TeamResponse>>builder()
                .status("SUCCESS")
                .data(teams)
                .build();
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<TeamResponse>> getAllTeamsByProjectId(@PathVariable("projectId") String projectId) {
        List<TeamResponse> teams = teamService.GetAllTeamByProjectId(projectId);
        return ApiResponse.<List<TeamResponse>>builder()
                .status("SUCCESS")
                .data(teams)
                .build();
    }

    @GetMapping("/{teamId}")
    public ApiResponse<TeamResponse> getTeamById(@PathVariable("teamId") Integer teamId) {
        TeamResponse team = teamService.GetTeamById(teamId);
        return ApiResponse.<TeamResponse>builder()
                .status("SUCCESS")
                .data(team)
                .build();
    }

}
