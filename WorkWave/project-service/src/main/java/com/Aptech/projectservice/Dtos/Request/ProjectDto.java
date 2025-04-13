package com.Aptech.projectservice.Dtos.Request;

import java.time.LocalDate;

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
public class ProjectDto {
    String projectId;
    String name;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    Integer statusId;
    String createdBy;
    String updatedBy;
}
