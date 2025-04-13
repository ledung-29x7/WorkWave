package com.Aptech.userservice.Services.Interfaces;

import java.util.List;

import com.Aptech.userservice.Dtos.Request.RoleCreationRequest;
import com.Aptech.userservice.Dtos.Request.RoleUpdationRequest;
import com.Aptech.userservice.Dtos.Response.RoleResponse;

public interface RoleService {
    RoleResponse CreateRole(RoleCreationRequest request);

    List<RoleResponse> GetAllRole();

    RoleResponse GetRoleById(Integer roleId);

    RoleResponse UpdateRole(String roleId, RoleUpdationRequest request);

    void DeleteRole(Integer roleId);
}
