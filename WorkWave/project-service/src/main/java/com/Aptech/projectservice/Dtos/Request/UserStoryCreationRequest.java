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
public class UserStoryCreationRequest {
    Integer epicId;
    Integer sprintId;
    String userStoryName;
    String description;
    Integer priority;
    Integer status;
}
