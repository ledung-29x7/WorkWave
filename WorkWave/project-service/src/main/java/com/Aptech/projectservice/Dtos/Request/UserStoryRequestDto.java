package com.Aptech.projectservice.Dtos.Request;

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
public class UserStoryRequestDto {
    Integer epicId;
    Integer sprintId;
    String name;
    String description;
    Integer priorityId;
    Integer statusId;
    String createdBy;
    String updatedBy;
}
