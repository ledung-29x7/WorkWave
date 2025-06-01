package com.Aptech.userservice.Services.Interfaces;

import java.util.List;

import com.Aptech.userservice.Dtos.Request.ProjectRequest;
import com.Aptech.userservice.Dtos.Request.ProjectUpdateRequest;
import com.Aptech.userservice.Dtos.Response.ProjectResponse;

public interface IProjectService {
    ProjectResponse create(ProjectRequest request, String createdBy);

    List<ProjectResponse> getAllByUser(String userId);

    ProjectResponse getById(String id);

    void update(String id, ProjectUpdateRequest request);

    void delete(String id);
}
