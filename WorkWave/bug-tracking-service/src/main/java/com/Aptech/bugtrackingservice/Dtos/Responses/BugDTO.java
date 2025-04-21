package com.Aptech.bugtrackingservice.Dtos.Responses;

import java.time.LocalDateTime;

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
public class BugDTO {
    Integer bugId;
    String projectId;
    Integer storyId;
    Integer taskId;
    String title;
    String description;
    String reportedBy;
    String assignedTo;
    Integer severityId;
    Integer priorityId;
    Integer statusId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
