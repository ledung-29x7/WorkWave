package com.Aptech.userservice.Services.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.userservice.Dtos.Request.TeamCreationRequest;
import com.Aptech.userservice.Dtos.Response.TeamResponse;
import com.Aptech.userservice.Entitys.Team;
import com.Aptech.userservice.Exceptions.AppException;
import com.Aptech.userservice.Exceptions.ErrorCode;
import com.Aptech.userservice.Mapper.TeamMapper;
import com.Aptech.userservice.Repositorys.TeamRepository;
import com.Aptech.userservice.Services.Interfaces.TeamService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TeamServiceImplement implements TeamService {
    TeamRepository teamRepository;
    TeamMapper teamMapper;

    @Override
    public TeamResponse CreateTeam(TeamCreationRequest request) {
        if (teamRepository.existsByTeamName(request.getTeamName()) == 1) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Team team = teamMapper.toTeam(request);
        teamRepository.createTeam(team.getProjectId(), team.getTeamName());
        return teamMapper.toTeamResponse(team);
    }

    @Override
    public List<TeamResponse> GetAllTeam() {
        return teamRepository.GetAllTeam();
    }

    @Override
    public List<TeamResponse> GetAllTeamByProjectId(String projectId) {
        return teamRepository.GetAllTeamByProjectId(projectId);
    }

    @Override
    public TeamResponse GetTeamById(Integer teamId) {
        return teamRepository.GetTeamById(teamId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    }

    @Override
    public void DeleteTeam(Integer teamId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DeleteTeam'");
    }

}
