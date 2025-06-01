package com.Aptech.userservice.Services.Interfaces;

import java.util.List;

import com.Aptech.userservice.Dtos.Request.ProjectMemberRequest;
import com.Aptech.userservice.Dtos.Response.ProjectMemberResponse;

public interface IProjectMemberService {
    List<ProjectMemberResponse> getMembers(String projectId);

    void assignUser(String projectId, ProjectMemberRequest request);

    void assignUser(String projectId, String userId, int roleId);

    void updateRole(String projectId, String userId, Integer roleId);

    void removeUser(String projectId, String userId);
}
