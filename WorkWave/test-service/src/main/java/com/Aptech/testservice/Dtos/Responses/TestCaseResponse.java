package com.Aptech.testservice.Dtos.Responses;

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
public class TestCaseResponse {
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
