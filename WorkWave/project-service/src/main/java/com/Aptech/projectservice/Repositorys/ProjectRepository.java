package com.Aptech.projectservice.Repositorys;

import java.time.LocalDate;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.projectservice.Entitys.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
        @Modifying
        @Transactional
        @Query(value = "CALL sp_create_project(:projectId, :name, :description, :startDate, :endDate, :statusId, :createdBy, :updatedBy)", nativeQuery = true)
        void createProject(@Param("projectId") String projectId,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("statusId") Integer statusId,
                        @Param("createdBy") String createdBy,
                        @Param("updatedBy") String updatedBy);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_update_project(:projectId, :name, :description, :startDate, :endDate, :statusId, :updatedBy)", nativeQuery = true)
        void updateProject(@Param("projectId") String projectId,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("statusId") Integer statusId,
                        @Param("updatedBy") String updatedBy);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_delete_project(:projectId)", nativeQuery = true)
        void deleteProject(@Param("projectId") String projectId);

        @Query(value = "CALL sp_get_project_by_id(:projectId)", nativeQuery = true)
        Project findProjectById(@Param("projectId") String projectId);

        @Query(value = "CALL sp_get_all_projects()", nativeQuery = true)
        List<Project> findAllProjects();

        @Query(value = "CALL existProjectById(:projectId)", nativeQuery = true)
        Integer existProjectById(@Param("projectId") String projectId);
}
