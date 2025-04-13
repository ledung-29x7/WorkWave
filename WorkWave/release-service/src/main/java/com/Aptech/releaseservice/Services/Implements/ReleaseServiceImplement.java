package com.Aptech.releaseservice.Services.Implements;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Aptech.releaseservice.Dtos.Responses.ReleaseManagementDTO;
import com.Aptech.releaseservice.Mappers.ReleaseManagementMapper;
import com.Aptech.releaseservice.Repositorys.ReleaseRepository;
import com.Aptech.releaseservice.Services.Interfaces.ReleaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReleaseServiceImplement implements ReleaseService {
    private final ReleaseRepository releaseRepository;
    private final ReleaseManagementMapper releaseManagementMapper;

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
    public ReleaseManagementDTO getReleaseById(Integer releaseId) {
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
    public List<ReleaseManagementDTO> getReleasesByProjectId(String projectId) {
        return releaseRepository.getReleasesByProjectId(projectId);
    }
}
