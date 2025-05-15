package com.Aptech.testservice.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "UserStoryLookup")
public class UserStoryLookup {
    @Id
    private Integer storyId;

    private String name;
    private String description;

    private Integer epicId;
    private Integer priorityId;
    private Integer statusId;

    private String createdBy;
    private LocalDateTime createdAt;
}
