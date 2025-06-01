package com.Aptech.userservice.Repositorys;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Entitys.Project;

import jakarta.transaction.Transactional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
        @Modifying
        @Transactional
        @Query(value = "CALL sp_create_project(:id, :name, :description, :createdBy, :startDate, :endDate, :statusId )", nativeQuery = true)
        void createProject(@Param("id") String id,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("createdBy") String createdBy,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("statusId") Integer statusId);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_update_project(:id, :name, :description, :startDate, :endDate, :statusId, :updatedBy)", nativeQuery = true)
        void updateProject(@Param("id") String id,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("statusId") Integer statusId,
                        @Param("updatedBy") String updatedBy);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_delete_project(:id)", nativeQuery = true)
        void deleteProject(@Param("id") String id);

        @Query(value = "CALL sp_find_projects_by_user(:userId)", nativeQuery = true)
        List<Project> findProjectsByUser(@Param("userId") String userId);

}
