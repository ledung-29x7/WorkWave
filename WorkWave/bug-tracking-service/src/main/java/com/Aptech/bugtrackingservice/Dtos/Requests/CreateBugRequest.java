package com.Aptech.bugtrackingservice.Dtos.Requests;

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
public class CreateBugRequest {
    String projectId;
    Integer storyId;
    Integer taskId;
    String title;
    String description;
    String assignedTo;
    Integer severityId;
    Integer priorityId;
    Integer statusId;
}
