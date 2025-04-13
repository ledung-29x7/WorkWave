package com.Aptech.bugtrackingservice.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.Aptech.bugtrackingservice.Dtos.Responses.BugDetailsDTO;

@Component
public class BugDetailsMapper {

    public BugDetailsDTO fromMap(Map<String, Object> row) {
        BugDetailsDTO dto = new BugDetailsDTO();

        dto.setBugId((Integer) row.get("BugId"));
        dto.setTitle((String) row.get("Title"));
        dto.setDescription((String) row.get("Description"));
        dto.setProjectId((String) row.get("ProjectId"));
        dto.setStoryId(row.get("StoryId") != null ? ((Number) row.get("StoryId")).intValue() : null);
        dto.setTaskId(row.get("TaskId") != null ? ((Number) row.get("TaskId")).intValue() : null);
        dto.setReportedBy((String) row.get("ReportedBy"));
        dto.setAssignedTo((String) row.get("AssignedTo"));

        dto.setSeverityId(row.get("SeverityId") != null ? ((Number) row.get("SeverityId")).intValue() : null);
        dto.setSeverityName((String) row.get("SeverityName"));

        dto.setPriorityId(row.get("PriorityId") != null ? ((Number) row.get("PriorityId")).intValue() : null);
        dto.setPriorityName((String) row.get("PriorityName"));

        dto.setStatusId(row.get("StatusId") != null ? ((Number) row.get("StatusId")).intValue() : null);
        dto.setStatusName((String) row.get("StatusName"));

        dto.setCreatedBy((String) row.get("CreatedBy"));
        dto.setUpdatedBy((String) row.get("UpdatedBy"));

        dto.setCreatedAt(toLocalDateTime(row.get("CreatedAt")));
        dto.setUpdatedAt(toLocalDateTime(row.get("UpdatedAt")));

        return dto;
    }

    private LocalDateTime toLocalDateTime(Object obj) {
        if (obj instanceof Timestamp) {
            return ((Timestamp) obj).toLocalDateTime();
        } else if (obj instanceof LocalDateTime) {
            return (LocalDateTime) obj;
        }
        return null;
    }
}
