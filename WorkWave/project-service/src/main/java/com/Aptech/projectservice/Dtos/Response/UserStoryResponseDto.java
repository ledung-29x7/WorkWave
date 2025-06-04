package com.Aptech.projectservice.Dtos.Response;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserStoryResponseDto {
    Integer storyId;
    Integer epicId;
    Integer sprintId;
    String name;
    String description;
    Integer priorityId;
    String assignedTo;
    Integer statusId;
    Date createdAt;
    Date updatedAt;
    String createdBy;
    String updatedBy;
    String projectId;
}
