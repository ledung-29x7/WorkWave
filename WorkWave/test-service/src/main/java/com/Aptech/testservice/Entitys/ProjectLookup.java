package com.Aptech.testservice.Entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ProjectLookup")
public class ProjectLookup {
    @Id
    private String projectId;

    private String name;
    private String description;
    private String createdBy;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer statusId;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
