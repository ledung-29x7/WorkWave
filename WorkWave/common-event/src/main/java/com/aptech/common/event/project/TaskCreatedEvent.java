package com.aptech.common.event.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreatedEvent {
    private Integer taskId;
    private Integer storyId;
    private String name;
    private String description;
    private Integer statusId;
    private Integer estimatedHours;
    private Integer loggedHours;
    private Integer remainingHours;
    private String assignedTo;
    private String createdBy;
}