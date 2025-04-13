package com.Aptech.projectservice.Repositorys;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.Aptech.projectservice.Dtos.Response.EpicResponseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.projectservice.Entitys.Epic;

import jakarta.transaction.Transactional;

@Repository
public interface EpicRepository extends JpaRepository<Epic, Integer> {
        @Modifying
        @Transactional
        @Query(value = "CALL CreateEpic(:projectId, :epicName, :description, :status, :startDate, :endDate, :createdBy)", nativeQuery = true)
        void createEpic(
                        @Param("projectId") String projectId,
                        @Param("epicName") String epicName,
                        @Param("description") String description,
                        @Param("status") Integer status,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("createdBy") String createdBy);

        @Query(value = "CALL GetEpicById(:epicId)", nativeQuery = true)
        Optional<EpicResponseProjection> getEpicById(@Param("epicId") Integer epicId);

        @Modifying
        @Transactional
        @Query(value = "CALL UpdateEpic(:epicId, :epicName, :description, :status, :startDate, :endDate, :updatedBy)", nativeQuery = true)
        void updateEpic(
                        @Param("epicId") Integer epicId,
                        @Param("epicName") String epicName,
                        @Param("description") String description,
                        @Param("status") Integer status,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("updatedBy") String updatedBy);

        @Modifying
        @Transactional
        @Query(value = "CALL DeleteEpic(:epicId)", nativeQuery = true)
        void deleteEpic(@Param("epicId") Integer epicId);

        @Query(value = "CALL GetEpicsByProject(:projectId)", nativeQuery = true)
        List<EpicResponseProjection> getEpicsByProject(@Param("projectId") String projectId);

}
