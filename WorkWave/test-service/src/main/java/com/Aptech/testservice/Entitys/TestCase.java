package com.Aptech.testservice.Entitys;

import java.time.LocalDateTime;

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
@Table(name = "TestCase")
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testCaseId;

    private String projectId;
    private Integer storyId;
    private String testName;
    private String description;
    private String expectedResult;
    private String actualResult;

    private Integer statusId;
    private String createdBy;
    private String executedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
