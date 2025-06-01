package com.Aptech.userservice.Dtos.Response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {
    private String projectId;
    private String name;
    private String description;
    private String createdBy;
    private LocalDateTime createdAt;
}
