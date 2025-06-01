package com.Aptech.projectservice.Services.Implements;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Aptech.projectservice.Dtos.Request.EpicCreationRequest;
import com.Aptech.projectservice.Dtos.Response.EpicResponse;
import com.Aptech.projectservice.Entitys.Epic;
import com.Aptech.projectservice.Exceptions.AppException;
import com.Aptech.projectservice.Exceptions.ErrorCode;
import com.Aptech.projectservice.Mappers.EpicMapper;
import com.Aptech.projectservice.Repositorys.EpicRepository;
import com.Aptech.projectservice.Repositorys.ProjectRepository;
import com.Aptech.projectservice.Services.Interfaces.EpicService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EpicImplement implements EpicService {
    EpicRepository epicRepository;
    ProjectRepository projectRepository;
    EpicMapper epicMapper;

    public List<EpicResponse> getEpicsByProject(String projectId) {
        return epicRepository.getEpicsByProject(projectId)
                .stream()
                .map(epicMapper::toDto)
                .toList();
    }

    public EpicResponse getEpicById(Integer id) {
        return epicRepository.getEpicById(id)
                .map(epicMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Epic not found"));
    }

    @Transactional
    public void createEpic(String projectId, EpicCreationRequest dto, String createdBy) {
        if (projectRepository.existProjectById(projectId) == 1) {
            epicRepository.createEpic(
                    projectId,
                    dto.getName(),
                    dto.getDescription(),
                    dto.getStatusId(),
                    dto.getStartDate(),
                    dto.getEndDate(),
                    createdBy);
        } else {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }

    @Transactional
    public void updateEpic(Integer id, EpicCreationRequest dto, String updatedBy) {
        epicRepository.updateEpic(
                id,
                dto.getName(),
                dto.getDescription(),
                dto.getStatusId(),
                dto.getStartDate(),
                dto.getEndDate(),
                updatedBy);
    }

    @Transactional
    public void deleteEpic(Integer id) {
        epicRepository.deleteEpic(id);
    }
}
