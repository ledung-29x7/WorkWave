package com.Aptech.releaseservice.Entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "ReleaseManagement")
public class ReleaseManagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReleaseId")
    Integer releaseId;

    @Column(name = "ProjectId")
    String projectId;

    @Column(name = "Version")
    String version;

    @Column(name = "Description")
    String description;

    @Column(name = "ReleaseDate")
    LocalDate releaseDate;

    @Column(name = "StatusId")
    Integer statusId;

    @Column(name = "CreatedAt")
    LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    LocalDateTime updatedAt;

    @Column(name = "CreatedBy")
    String createdBy;

    @Column(name = "UpdatedBy")
    String updatedBy;
}
