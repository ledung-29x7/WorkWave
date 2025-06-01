package com.Aptech.userservice.Dtos.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectMemberResponse {
    private String userId;
    private String userName;
    private Integer roleId;
    private String roleName;
}
