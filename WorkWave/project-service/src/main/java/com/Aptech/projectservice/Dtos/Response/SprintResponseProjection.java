package com.Aptech.projectservice.Dtos.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SprintResponseProjection {
    Integer getSprintId();

    String getProjectId();

    String getName();

    LocalDate getStartDate();

    LocalDate getEndDate();

    Integer getStatusId();

    String getGoal();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    String getCreatedBy();

    String getUpdatedBy();
}
