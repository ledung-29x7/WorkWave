package com.Aptech.bugtrackingservice.Repositorys;

import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.bugtrackingservice.Entitys.Bug;

@Repository
public interface BugRepository extends JpaRepository<Bug, Integer> {

        @Modifying
        @Transactional
        @Query(value = "CALL sp_create_bug(:projectId, :storyId, :taskId, :title, :description, :reportedBy, :assignedTo, :severityId, :priorityId, :statusId, :createdBy)", nativeQuery = true)
        void createBug(
                        @Param("projectId") String projectId,
                        @Param("storyId") Integer storyId,
                        @Param("taskId") Integer taskId,
                        @Param("title") String title,
                        @Param("description") String description,
                        @Param("reportedBy") String reportedBy,
                        @Param("assignedTo") String assignedTo,
                        @Param("severityId") Integer severityId,
                        @Param("priorityId") Integer priorityId,
                        @Param("statusId") Integer statusId,
                        @Param("createdBy") String createdBy);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_update_bug(:bugId, :title, :description, :assignedTo, :severityId, :priorityId, :statusId, :updatedBy)", nativeQuery = true)
        void updateBug(
                        @Param("bugId") Integer bugId,
                        @Param("title") String title,
                        @Param("description") String description,
                        @Param("assignedTo") String assignedTo,
                        @Param("severityId") Integer severityId,
                        @Param("priorityId") Integer priorityId,
                        @Param("statusId") Integer statusId,
                        @Param("updatedBy") String updatedBy);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_delete_bug(:bugId)", nativeQuery = true)
        void deleteBug(@Param("bugId") Integer bugId);

        @Query(value = "CALL sp_get_bugs_by_project_full(:projectId)", nativeQuery = true)
        List<Map<String, Object>> getBugsByProject(@Param("projectId") String projectId);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_update_bug_with_history(:bugId, :title, :description, :assignedTo, " +
                        ":severityId, :priorityId, :statusId, :updatedBy, :comment)", nativeQuery = true)
        void updateBugWithHistory(@Param("bugId") Integer bugId,
                        @Param("title") String title,
                        @Param("description") String description,
                        @Param("assignedTo") String assignedTo,
                        @Param("severityId") Integer severityId,
                        @Param("priorityId") Integer priorityId,
                        @Param("statusId") Integer statusId,
                        @Param("updatedBy") String updatedBy,
                        @Param("comment") String comment);

        @Query(value = "CALL sp_get_bug_details_by_id(:bugId)", nativeQuery = true)
        Map<String, Object> getBugDetailsById(@Param("bugId") Integer bugId);
}
