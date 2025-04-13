package com.Aptech.projectservice.Dtos.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EpicResponseProjection {
    Integer getEpicId();

    String getProjectId();

    String getName();

    String getDescription();

    Integer getStatusId();

    LocalDate getStartDate();

    LocalDate getEndDate();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    String getCreatedBy();

    String getUpdatedBy();
}
