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
    Integer testCaseId;
    String projectId;
    Integer storyId;
    String testName;
    String description;
    String expectedResult;
    String actualResult;
    Integer statusId;
    String createdBy;
    String executedBy;
}
