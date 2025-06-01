package com.Aptech.userservice.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ProjectRoleAssignment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRoleAssignment {
    @Id
    private String assignmentId;

    private String userId;
    private String projectId;
    private Integer roleId;
    private LocalDateTime assignedAt;
}
