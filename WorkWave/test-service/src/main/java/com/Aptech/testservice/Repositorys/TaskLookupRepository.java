package com.Aptech.testservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.testservice.Entitys.TaskLookup;

import jakarta.transaction.Transactional;

@Repository
public interface TaskLookupRepository extends JpaRepository<TaskLookup, Integer> {

    @Modifying
    @Transactional
    @Query(value = "CALL saveTaskLookup(:taskId, :storyId, :name, :description, :statusId, :estimatedHours, :loggedHours, :remainingHours, :assignedTo, :createdBy)", nativeQuery = true)
    void saveTaskLookup(@Param("taskId") Integer taskId,
            @Param("storyId") Integer storyId,
            @Param("name") String name,
            @Param("description") String description,
            @Param("statusId") Integer statusId,
            @Param("estimatedHours") Integer estimatedHours,
            @Param("loggedHours") Integer loggedHours,
            @Param("remainingHours") Integer remainingHours,
            @Param("assignedTo") String assignedTo,
            @Param("createdBy") String createdBy);

    @Modifying
    @Transactional
    @Query(value = "CALL updateTaskLookup(:taskId, :name, :description, :statusId, :estimatedHours, :loggedHours, :remainingHours, :assignedTo, :updatedBy)", nativeQuery = true)
    void updateTaskLookup(@Param("taskId") Integer taskId,
            @Param("name") String name,
            @Param("description") String description,
            @Param("statusId") Integer statusId,
            @Param("estimatedHours") Integer estimatedHours,
            @Param("loggedHours") Integer loggedHours,
            @Param("remainingHours") Integer remainingHours,
            @Param("assignedTo") String assignedTo,
            @Param("updatedBy") String updatedBy);

    @Modifying
    @Transactional
    @Query(value = "CALL deleteTaskLookup(:taskId)", nativeQuery = true)
    void deleteTaskLookup(@Param("taskId") Integer taskId);

    @Query(value = "CALL ExistsByTaskLookupId(:taskId)", nativeQuery = true)
    int ExistsByTaskLookupId(@Param("taskId") Integer taskId);
}
