package com.Aptech.releaseservice.Services.Implements;

import java.util.List;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.Aptech.releaseservice.Dtos.Requests.ReleaseManagementDTO;
import com.Aptech.releaseservice.Dtos.Responses.ReleaseResponseDTO;
import com.Aptech.releaseservice.Mappers.ReleaseManagementMapper;
import com.Aptech.releaseservice.Repositorys.ReleaseRepository;
import com.Aptech.releaseservice.Services.Interfaces.ReleaseService;

import lombok.RequiredArgsConstructor;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ReleaseServiceImplement implements ReleaseService {
    ReleaseRepository releaseRepository;
    ReleaseManagementMapper releaseManagementMapper;

    // Tạo Release mới
    public void createRelease(ReleaseManagementDTO releaseManagementDTO) {
        releaseRepository.createRelease(
                releaseManagementDTO.getProjectId(),
                releaseManagementDTO.getVersion(),
                releaseManagementDTO.getDescription(),
                releaseManagementDTO.getReleaseDate(),
                releaseManagementDTO.getStatusId(),
                releaseManagementDTO.getCreatedBy());
    }

    // Lấy Release theo ID
    public ReleaseResponseDTO getReleaseById(Integer releaseId) {
        return releaseRepository.getReleaseById(releaseId);
    }

    // Cập nhật Release
    public void updateRelease(Integer releaseId, ReleaseManagementDTO releaseManagementDTO) {
        releaseRepository.updateRelease(
                releaseId,
                releaseManagementDTO.getVersion(),
                releaseManagementDTO.getDescription(),
                releaseManagementDTO.getReleaseDate(),
                releaseManagementDTO.getStatusId(),
                releaseManagementDTO.getUpdatedBy());
    }

    // Xóa Release
    public void deleteRelease(Integer releaseId) {
        releaseRepository.deleteRelease(releaseId);
    }

    // Lấy tất cả Releases của Project
    public List<ReleaseResponseDTO> getReleasesByProjectId(String projectId) {
        return releaseRepository.getReleasesByProjectId(projectId);
    }
}
