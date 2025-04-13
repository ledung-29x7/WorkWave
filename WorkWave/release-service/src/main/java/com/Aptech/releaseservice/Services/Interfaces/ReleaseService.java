package com.Aptech.releaseservice.Services.Interfaces;

import java.util.List;

import com.Aptech.releaseservice.Dtos.Responses.ReleaseManagementDTO;

public interface ReleaseService {
    public void createRelease(ReleaseManagementDTO releaseManagementDTO);

    public ReleaseManagementDTO getReleaseById(Integer releaseId);

    public void updateRelease(Integer releaseId, ReleaseManagementDTO releaseManagementDTO);

    public void deleteRelease(Integer releaseId);

    public List<ReleaseManagementDTO> getReleasesByProjectId(String projectId);
}