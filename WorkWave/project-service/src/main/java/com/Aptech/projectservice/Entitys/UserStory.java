package com.Aptech.projectservice.Entitys;

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

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "UserStory")
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StoryId")
    Integer userStoryId;
    @Column(name = "EpicId")
    Integer epicId;
    @Column(name = "SprintId")
    Integer sprintId;
    @Column(name = "Name")
    String userStoryName;
    @Column(name = "Description")
    String description;
    @Column(name = "Priority")
    Integer priority;
    @Column(name = "Status")
    Integer status;
    @Column(name = "CreatedAt")
    Date createdAt;
    @Column(name = "UpdatedAt")
    Date updatedAt;
    @Column(name = "CreatedBy")
    String createdBy;
    @Column(name = "UpdatedBy")
    String updatedBy;
}
