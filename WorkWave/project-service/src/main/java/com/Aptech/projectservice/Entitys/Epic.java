package com.Aptech.projectservice.Entitys;

import java.util.Date;

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
@Table(name = "Epic")
public class Epic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EpicId")
    Integer epicId;
    @Column(name = "ProjectId")
    String projectId;
    @Column(name = "Name")
    String epicName;
    @Column(name = "Description")
    String description;
    @Column(name = "Status")
    Integer status;
    @Column(name = "StartDate")
    Date startDate;
    @Column(name = "EndDate")
    Date endDate;
    @Column(name = "CreatedAt")
    Date createdAt;
    @Column(name = "UpdatedAt")
    Date updatedAt;
    @Column(name = "CreateBy")
    String createdBy;
    @Column(name = "UpdatedBy")
    String updatedBy;
}
