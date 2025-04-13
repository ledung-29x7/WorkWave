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
    ApiResponse<TeamResponse> CreateTeam(@RequestBody TeamCreationRequest request) {
        return ApiResponse.<TeamResponse>builder()
                .result(teamService.CreateTeam(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<TeamResponse>> GetAllTeams() {
        return ApiResponse.<List<TeamResponse>>builder()
                .result(teamService.GetAllTeam())
                .build();
    }


    @GetMapping("/projectId")
    ApiResponse<List<TeamResponse>> GetAllTeamsByProjectId(@PathVariable("projectId") String projectId ) {
        return ApiResponse.<List<TeamResponse>>builder()
                .result(teamService.GetAllTeamByProjectId(projectId))
                .build();
    }

    @GetMapping("/{teamId}")
    ApiResponse<TeamResponse> GetTeamById(@PathVariable("teamId") Integer teamId) {
        return ApiResponse.<TeamResponse>builder()
                .result(teamService.GetTeamById(teamId))
                .build();
    }

}
