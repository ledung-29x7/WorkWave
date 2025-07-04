package com.Aptech.userservice.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Project")
public class Project {
    @Id
    private String projectId;
    private String name;
    private String description;
    private String createdBy;
    private LocalDateTime createdAt;
}
