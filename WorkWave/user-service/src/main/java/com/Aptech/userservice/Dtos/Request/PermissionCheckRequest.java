package com.Aptech.userservice.Dtos.Request;

import lombok.Data;

@Data
public class PermissionCheckRequest {
    private String userId;
    private String projectId;
    private String permissionCode;
}
