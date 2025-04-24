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
public class TaskResponseDto {
    Integer taskId;
    Integer storyId;
    String assignedTo;
    String name;
    String description;
    Integer statusId;
    Integer estimatedHours;
    Integer loggedHours;
    Integer remainingHours;
    Date createdAt;
    Date updatedAt;
    String createdBy;
    String updatedBy;
}
