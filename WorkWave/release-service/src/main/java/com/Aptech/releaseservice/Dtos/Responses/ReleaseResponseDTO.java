package com.Aptech.releaseservice.Dtos.Responses;

import java.sql.Date;
import java.sql.Timestamp;

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
public class ReleaseResponseDTO {
    Integer releaseId;
    String projectId;
    String version;
    String description;
    Date releaseDate;
    String statusName;
    Timestamp createdAt;
    Timestamp updatedAt;
    String createdBy;
    String updatedBy;
}
