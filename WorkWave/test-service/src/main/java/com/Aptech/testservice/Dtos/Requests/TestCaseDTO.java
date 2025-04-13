package com.Aptech.testservice.Dtos.Requests;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestCaseDTO {
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
