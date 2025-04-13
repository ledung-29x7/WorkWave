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
@Table(name = "Sprint")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SprintId")
    Integer sprintId;
    @Column(name = "ProjectId")
    String projectId;
    @Column(name = "Name")
    String sprintName;
    @Column(name = "StartDate")
    Date startDate;
    @Column(name = "EndDate")
    Date endDate;
    @Column(name = "Status")
    Integer status;
    @Column(name = "Goal")
    String goal;
    @Column(name = "CreatedAt")
    Date createdAt;
    @Column(name = "UpdatedAt")
    Date updatedAt;
    @Column(name = "CreatedBy")
    String createdBy;
    @Column(name = "UpdatedBy")
    String updatedBy;
}
