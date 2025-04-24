package com.Aptech.projectservice.Entitys;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskId")
    Integer taskId;
    @Column(name = "StoryId")
    Integer storyId;
    @Column(name = "AssignedTo")
    String assignedTo;
    @Column(name = "Name")
    String taskName;
    @Column(name = "Description")
    String description;
    @Column(name = "Status")
    Integer status;
    @Column(name = "EstimatedHours")
    Integer estimatedHours;
    @Column(name = "LoggedHours")
    Integer loggedHours;
    Date createdAt;
    @Column(name = "UpdatedAt")
    Date updatedAt;
    @Column(name = "CreatedBy")
    String createdBy;
    @Column(name = "UpdatedBy")
    String updatedBy;

}
