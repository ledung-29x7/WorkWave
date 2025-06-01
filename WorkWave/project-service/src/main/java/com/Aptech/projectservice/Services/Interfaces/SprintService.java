package com.Aptech.projectservice.Services.Interfaces;

import java.util.List;

import com.Aptech.projectservice.Dtos.Request.SprintRequestDto;
import com.Aptech.projectservice.Dtos.Response.SprintResponseDto;

public interface SprintService {
    public List<SprintResponseDto> getSprintsByProject(String projectId);

    public SprintResponseDto getSprintById(Integer id);

    public void createSprint(String projectId, SprintRequestDto dto, String createdBy);

    public void updateSprint(Integer id, SprintRequestDto dto, String updatedBy);

    public void deleteSprint(Integer id);
}
