package com.Aptech.releaseservice.Dtos.Requests;

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
public class ReleaseManagementDTO {
    Integer releaseId;
    String projectId;
    String version;
    String description;
    Date releaseDate;
    Integer statusId;
    Timestamp createdAt;
    Timestamp updatedAt;
}
