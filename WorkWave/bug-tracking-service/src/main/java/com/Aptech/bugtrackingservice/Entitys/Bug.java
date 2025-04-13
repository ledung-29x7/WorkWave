package com.Aptech.bugtrackingservice.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer bugId;
    String projectId;
    String title;
    String description;
    String reportedBy;
    String assignedTo;
    Integer severityId;
    Integer priorityId;
    Integer statusId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
