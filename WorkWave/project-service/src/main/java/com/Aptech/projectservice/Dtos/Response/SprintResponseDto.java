package com.Aptech.projectservice.Dtos.Response;

import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SprintResponseDto {
    Integer sprintId;
    String projectId;
    String name;
    Date startDate;
    Date endDate;
    Integer statusId;
    String goal;
    Date createdAt;
    Date updatedAt;
    String createdBy;
    String updatedBy;
}
