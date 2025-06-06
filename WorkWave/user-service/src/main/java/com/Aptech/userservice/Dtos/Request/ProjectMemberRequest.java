package com.Aptech.userservice.Dtos.Request;

import lombok.Data;

@Data
public class ProjectMemberRequest {
    private String email;
    private Integer roleId;
}
