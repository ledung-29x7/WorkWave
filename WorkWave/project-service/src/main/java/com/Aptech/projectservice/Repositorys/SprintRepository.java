package com.Aptech.projectservice.Repositorys;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.projectservice.Dtos.Response.SprintResponseProjection;
import com.Aptech.projectservice.Entitys.Sprint;

import jakarta.transaction.Transactional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {
        @Modifying
        @Transactional
        @Query(value = "CALL CreateSprint(:projectId, :sprintName, :startDate, :endDate, :status, :goal, :createdBy)", nativeQuery = true)
        void createSprint(
                        @Param("projectId") String projectId,
                        @Param("sprintName") String sprintName,
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate,
                        @Param("status") Integer status,
                        @Param("goal") String goal,
                        @Param("createdBy") String createdBy);

        @Query(value = "CALL GetSprintById(:sprintId)", nativeQuery = true)
        Optional<SprintResponseProjection> getSprintById(@Param("sprintId") Integer sprintId);

        @Modifying
        @Transactional
        @Query(value = "CALL UpdateSprint(:sprintId, :sprintName, :startDate, :endDate, :status, :goal, :updatedBy)", nativeQuery = true)
        void updateSprint(
                        @Param("sprintId") Integer sprintId,
                        @Param("sprintName") String sprintName,
                        @Param("startDate") Date startDate,
                        @Param("endDate") Date endDate,
                        @Param("status") Integer status,
                        @Param("goal") String goal,
                        @Param("updatedBy") String updatedBy);

        @Modifying
        @Transactional
        @Query(value = "CALL DeleteSprint(:sprintId)", nativeQuery = true)
        void deleteSprint(@Param("sprintId") Integer sprintId);

        @Query(value = "CALL GetSprintsByProject(:projectId)", nativeQuery = true)
        List<SprintResponseProjection> getSprintsByProject(@Param("projectId") String projectId);
}
