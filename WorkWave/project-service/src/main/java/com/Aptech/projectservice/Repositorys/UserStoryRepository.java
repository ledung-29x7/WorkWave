package com.Aptech.projectservice.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.projectservice.Entitys.UserStory;

import jakarta.transaction.Transactional;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Integer> {
        @Modifying
        @Transactional
        @Query(value = "CALL CreateUserStory(:epicId, :sprintId, :name, :description, :priorityId, :assignedTo, :statusId, :createdBy, :projectId)", nativeQuery = true)
        void createUserStory(
                        @Param("epicId") Integer epicId,
                        @Param("sprintId") Integer sprintId,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("priorityId") Integer priorityId,
                        @Param("assignedTo") String assignedTo,
                        @Param("statusId") Integer statusId,
                        @Param("createdBy") String createdBy,
                        @Param("projectId") String projectId);

        @Query(value = "CALL GetUserStoryById(:storyId)", nativeQuery = true)
        UserStory getUserStoryById(@Param("storyId") Integer storyId);

        @Modifying
        @Transactional
        @Query(value = "CALL UpdateUserStory(:storyId, :epicId, :sprintId, :name, :description, :priorityId, :assignedTo, :statusId, :updatedBy)", nativeQuery = true)
        void updateUserStory(
                        @Param("storyId") Integer storyId,
                        @Param("epicId") Integer epicId,
                        @Param("sprintId") Integer sprintId,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("priorityId") Integer priorityId,
                        @Param("assignedTo") String assignedTo,
                        @Param("statusId") Integer statusId,
                        @Param("updatedBy") String updatedBy);

        @Modifying
        @Transactional
        @Query(value = "CALL DeleteUserStory(:storyId)", nativeQuery = true)
        void deleteUserStory(@Param("storyId") Integer storyId);

        @Query(value = "CALL GetUserStoriesByEpicId(:epicId)", nativeQuery = true)
        List<UserStory> getUserStoriesByEpicId(@Param("epicId") Integer epicId);

        @Query(value = "CALL getLatestCreatedStoryId(:createdBy)", nativeQuery = true)
        Integer getLatestCreatedStoryId(@Param("createdBy") String createdBy);

        @Query(value = "CALL GetUserStoriesByUserId(:assignedTo)", nativeQuery = true)
        List<UserStory> getUserStoriesByUserId(@Param("assignedTo") String assignedTo);

        @Query(value = "CALL GetUserStoriesByProjectId(:projectId)", nativeQuery = true)
        List<UserStory> getUserStoriesByProjectId(@Param("projectId") String projectId);

        @Query(value = "CALL GetUserStoriesBySprintId(:sprintId)", nativeQuery = true)
        List<UserStory> getUserStoriesBySprintId(@Param("sprintId") Integer projectId);
}
