package com.Aptech.bugtrackingservice.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TaskLookup")
public class TaskLookup {
    @Id
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

    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
