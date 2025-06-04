package com.Aptech.testservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aptech.testservice.Entitys.UserStoryLookup;

import jakarta.transaction.Transactional;

public interface UserStoryLookupRepository extends JpaRepository<UserStoryLookup, Integer> {
        @Modifying
        @Transactional
        @Query(value = "CALL saveUserStoryLookup(:storyId, :name, :description, :epicId, :priorityId, :assignedTo, :statusId, :createdBy, :projectId)", nativeQuery = true)
        void saveUserStoryLookup(
                        @Param("storyId") Integer storyId,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("epicId") Integer epicId,
                        @Param("priorityId") Integer priorityId,
                        @Param("assignedTo") String assignedTo,
                        @Param("statusId") Integer statusId,
                        @Param("createdBy") String createdBy,
                        @Param("projectId") String projectId);

        @Query(value = "CALL ExistsByUserStoryLookupId(:storyId)", nativeQuery = true)
        int ExistsByUserStoryLookupId(@Param("storyId") Integer storyId);

        @Modifying
        @Transactional
        @Query(value = "CALL updateUserStoryLookup(:storyId, :name, :description, :epicId, :priorityId, :assignedTo, :statusId)", nativeQuery = true)
        void updateUserStoryLookup(@Param("storyId") Integer storyId,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("epicId") Integer epicId,
                        @Param("priorityId") Integer priorityId,
                        @Param("assignedTo") String assignedTo,
                        @Param("statusId") Integer statusId);

        @Modifying
        @Transactional
        @Query(value = "CALL deleteUserStoryLookup(:storyId)", nativeQuery = true)
        void deleteUserStoryLookup(@Param("storyId") Integer storyId);
}
