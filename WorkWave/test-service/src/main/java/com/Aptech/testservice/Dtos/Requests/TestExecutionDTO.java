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
public class TestExecutionDTO {
    private Integer testExecutionId;
    private Integer testCaseId;
    private String executedBy;
    private LocalDateTime executionDate;
    private Integer statusId;
    private String comment;
}
