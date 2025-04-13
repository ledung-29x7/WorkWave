package com.Aptech.userservice.Mapper;

import org.mapstruct.Mapper;

import com.Aptech.userservice.Dtos.Request.TeamCreationRequest;
import com.Aptech.userservice.Dtos.Response.TeamResponse;
import com.Aptech.userservice.Entitys.Team;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    Team toTeam(TeamCreationRequest request);

    TeamResponse toTeamResponse(Team team);
}
