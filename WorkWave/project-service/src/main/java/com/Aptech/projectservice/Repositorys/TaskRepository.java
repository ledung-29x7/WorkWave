package com.Aptech.projectservice.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aptech.projectservice.Entitys.Task;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Modifying
    @Transactional
    @Query(value = "CALL CreateTask(:storyId, :assignedTo, :name, :description, :statusId, :estimatedHours, :createdBy)", nativeQuery = true)
    void createTask(
            @Param("storyId") Integer storyId,
            @Param("assignedTo") String assignedTo,
            @Param("name") String name,
            @Param("description") String description,
            @Param("statusId") Integer statusId,
            @Param("estimatedHours") Integer estimatedHours,
            @Param("createdBy") String createdBy);

    @Query(value = "CALL GetTaskById(:taskId)", nativeQuery = true)
    Task getTaskById(@Param("taskId") Integer taskId);

    @Modifying
    @Transactional
    @Query(value = "CALL UpdateTask(:taskId, :assignedTo, :name, :description, :statusId, :estimatedHours, :loggedHours, :remainingHours, :updatedBy)", nativeQuery = true)
    void updateTask(
            @Param("taskId") Integer taskId,
            @Param("assignedTo") String assignedTo,
            @Param("name") String name,
            @Param("description") String description,
            @Param("statusId") Integer statusId,
            @Param("estimatedHours") Integer estimatedHours,
            @Param("loggedHours") Integer loggedHours,
            @Param("remainingHours") Integer remainingHours,
            @Param("updatedBy") String updatedBy);

    @Modifying
    @Transactional
    @Query(value = "CALL DeleteTask(:taskId)", nativeQuery = true)
    void deleteTask(@Param("taskId") Integer taskId);

    @Query(value = "CALL GetTasksByStoryId(:storyId)", nativeQuery = true)
    List<Task> getTasksByStoryId(@Param("storyId") Integer storyId);
}
