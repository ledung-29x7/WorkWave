package com.Aptech.releaseservice.Services.Interfaces;

import java.util.List;

import com.Aptech.releaseservice.Dtos.Requests.ReleaseManagementDTO;
import com.Aptech.releaseservice.Dtos.Responses.ReleaseResponseDTO;

public interface ReleaseService {
    public void createRelease(ReleaseManagementDTO releaseManagementDTO, String createdBy);

    public ReleaseResponseDTO getReleaseById(Integer releaseId);

    public void updateRelease(Integer releaseId, ReleaseManagementDTO releaseManagementDTO, String updatedBy);

    public void deleteRelease(Integer releaseId);

    public List<ReleaseResponseDTO> getReleasesByProjectId(String projectId);
}