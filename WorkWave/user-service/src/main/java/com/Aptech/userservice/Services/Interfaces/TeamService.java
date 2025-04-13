package com.Aptech.userservice.Services.Interfaces;

import java.util.List;

import com.Aptech.userservice.Dtos.Request.TeamCreationRequest;
import com.Aptech.userservice.Dtos.Response.TeamResponse;

public interface TeamService {
    TeamResponse CreateTeam(TeamCreationRequest request);

    List<TeamResponse> GetAllTeam();

    List<TeamResponse> GetAllTeamByProjectId(String projectId);

    TeamResponse GetTeamById(Integer teamId);

    // TeamResponse UpdateRole(String roleId, RoleUpdationRequest request);

    void DeleteTeam(Integer teamId);
}
