package com.Aptech.userservice.Services.Interfaces;

import java.util.List;

import com.Aptech.userservice.Dtos.Response.RolePermissionResponse;

public interface IRolePermissionService {
    List<RolePermissionResponse> getPermissionsByRole(Integer roleId);

    void addPermission(Integer roleId, Integer permissionId);

    void removePermission(Integer roleId, Integer permissionId);
}
