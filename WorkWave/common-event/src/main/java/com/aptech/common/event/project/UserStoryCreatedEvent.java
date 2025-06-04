package com.aptech.common.event.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStoryCreatedEvent {
    private Integer storyId;
    private String name;
    private String description;
    private Integer epicId;
    private Integer priorityId;
    private String assignedTo;
    private Integer statusId;
    private String createdBy;
    private String projectId;
}