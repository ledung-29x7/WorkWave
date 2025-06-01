package com.aptech.common.event.project;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdatedEvent {
    private String projectId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer statusId;
    private String updatedBy;

}
