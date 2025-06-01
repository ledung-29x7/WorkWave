package com.Aptech.userservice.Dtos.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionResponse {
    private Integer permissionId;
    private String code;
    private String description;
}
