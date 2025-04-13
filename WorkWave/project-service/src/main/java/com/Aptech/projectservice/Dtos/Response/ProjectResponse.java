package com.Aptech.projectservice.Dtos.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectResponse {
    String projectId;
    String projectName;
    String description;
    Date StartDate;
    Date EndDate;
    Integer Status;
}
