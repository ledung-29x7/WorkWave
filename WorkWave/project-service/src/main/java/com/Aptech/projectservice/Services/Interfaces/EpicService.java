package com.Aptech.projectservice.Services.Interfaces;

import java.util.List;

import com.Aptech.projectservice.Dtos.Request.EpicCreationRequest;
import com.Aptech.projectservice.Dtos.Response.EpicResponse;

public interface EpicService {
    public List<EpicResponse> getEpicsByProject(String projectId);

    public EpicResponse getEpicById(Integer id);

    public void createEpic(String projectId, EpicCreationRequest dto, String createdBy);

    public void updateEpic(Integer id, EpicCreationRequest dto, String updatedBy);

    public void deleteEpic(Integer id);
}
