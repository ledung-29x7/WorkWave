package com.Aptech.projectservice.Services.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Aptech.projectservice.Dtos.Request.SprintRequestDto;
import com.Aptech.projectservice.Dtos.Response.SprintResponseDto;
import com.Aptech.projectservice.Dtos.Response.SprintResponseProjection;
import com.Aptech.projectservice.Exceptions.AppException;
import com.Aptech.projectservice.Exceptions.ErrorCode;
import com.Aptech.projectservice.Mappers.SprintMapper;
import com.Aptech.projectservice.Repositorys.ProjectRepository;
import com.Aptech.projectservice.Repositorys.SprintRepository;
import com.Aptech.projectservice.Services.Interfaces.SprintService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SprintImplement implements SprintService {
    SprintRepository sprintRepository;
    ProjectRepository projectRepository;
    SprintMapper sprintMapper;

    public List<SprintResponseDto> getSprintsByProject(String projectId) {
        return sprintRepository.getSprintsByProject(projectId)
                .stream()
                .map(sprintMapper::toDto)
                .toList();
    }

    public SprintResponseDto getSprintById(Integer id) {
        Optional<SprintResponseProjection> sprint = sprintRepository.getSprintById(id);
        return sprint.map(sprintMapper::toDto).orElseThrow(() -> new RuntimeException("Sprint not found"));
    }

    @Transactional
    public void createSprint(String projectId, SprintRequestDto dto) {
        if (projectRepository.existProjectById(projectId) == 1) {

            sprintRepository.createSprint(
                    projectId,
                    dto.getName(),
                    dto.getStartDate(),
                    dto.getEndDate(),
                    dto.getStatusId(),
                    dto.getGoal(),
                    dto.getCreatedBy());
        } else {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }

    @Transactional
    public void updateSprint(Integer id, SprintRequestDto dto) {
        sprintRepository.updateSprint(
                id,
                dto.getName(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getStatusId(),
                dto.getGoal(),
                dto.getUpdatedBy());
    }

    @Transactional
    public void deleteSprint(Integer id) {
        sprintRepository.deleteSprint(id);
    }
}
