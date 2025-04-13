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
public class BugDetailsDTO {
    private Integer bugId;
    String title;
    String description;
    String projectId;
    Integer storyId;
    Integer taskId;
    String reportedBy;
    String assignedTo;

    Integer severityId;
    String severityName;

    Integer priorityId;
    String priorityName;

    Integer statusId;
    String statusName;

    String createdBy;
    String updatedBy;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
