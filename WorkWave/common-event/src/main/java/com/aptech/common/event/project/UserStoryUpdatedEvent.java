package com.aptech.common.event.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStoryUpdatedEvent {
    private Integer storyId;
    private String name;
    private String description;
    private Integer epicId;
    private Integer priorityId;
    private Integer statusId;
}
