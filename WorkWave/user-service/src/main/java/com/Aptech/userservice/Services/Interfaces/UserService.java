package com.Aptech.userservice.Services.Interfaces;

import java.util.List;

import com.Aptech.userservice.Dtos.Request.AssignTeamRequest;
import com.Aptech.userservice.Dtos.Request.UserCreationRequest;
import com.Aptech.userservice.Dtos.Request.UserRoleCreationRequest;
import com.Aptech.userservice.Dtos.Response.GetAllUserResponse;
import com.Aptech.userservice.Dtos.Response.UserResponse;
import com.Aptech.userservice.Entitys.ProjectLookup;

public interface UserService {
    UserResponse CreateUser(UserCreationRequest request);

    GetAllUserResponse getAllUsers(int pageNumber, int pageSize, String searchName, String searchEmail);

    public UserResponse getUserById(String userId);

    void DeleteUser(String userId);

    void AssignRole(UserRoleCreationRequest request);

    void AssignTeam(AssignTeamRequest request);

    List<ProjectLookup> GetProjectsByUserId(String userId);

}
