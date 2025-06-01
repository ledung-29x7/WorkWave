package com.Aptech.userservice.Services.Interfaces;

public interface IPermissionCheckService {
    boolean checkPermission(String userId, String projectId, String permissionCode);
}
