package com.Aptech.bugtrackingservice.Dtos.Responses;

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
public class BugHistoryDTO {
    Integer bugHistoryId;
    Integer bugId;
    Integer statusId;
    String statusName;
    String updatedBy;
    LocalDateTime updatedAt;
    String comment;
}
