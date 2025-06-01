package com.Aptech.bugtrackingservice.Repositorys;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aptech.bugtrackingservice.Entitys.ProjectLookup;

import jakarta.transaction.Transactional;

public interface ProjectLookupRepository extends JpaRepository<ProjectLookup, String> {
        @Modifying
        @Transactional
        @Query(value = "CALL saveProjectLookup(:projectId, :name, :description, :createdBy,:startDate, :endDate, :statusId)", nativeQuery = true)
        void saveProjectLookup(
                        @Param("projectId") String projectId,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("createdBy") String createdBy,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("statusId") Integer statusId);

        @Query(value = "CALL ExistsByProjectId(:projectId)", nativeQuery = true)
        int ExistsByProjectId(@Param("projectId") String projectId);

        @Modifying
        @Transactional
        @Query(value = "CALL deleteProjectLookup(:projectId)", nativeQuery = true)
        void deleteProjectLookup(@Param("projectId") String projectId);

        @Modifying
        @Transactional
        @Query(value = "CALL updateProjectLookup(:projectId, :name, :description, :startDate, :endDate, :statusId, :updatedBy)", nativeQuery = true)
        void updateProjectLookup(@Param("projectId") String projectId,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("statusId") Integer statusId,
                        @Param("updatedBy") String updatedBy);

}
