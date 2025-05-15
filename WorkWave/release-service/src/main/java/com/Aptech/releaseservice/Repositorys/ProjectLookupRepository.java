package com.Aptech.releaseservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aptech.releaseservice.Entitys.ProjectLookup;

import jakarta.transaction.Transactional;

public interface ProjectLookupRepository extends JpaRepository<ProjectLookup, String> {
        @Modifying
        @Transactional
        @Query(value = "CALL saveProjectLookup(:projectId, :name, :description, :createdBy)", nativeQuery = true)
        void saveProjectLookup(
                        @Param("projectId") String projectId,
                        @Param("name") String name,
                        @Param("description") String description,
                        @Param("createdBy") String createdBy);

        @Query(value = "CALL ExistsByProjectId(:projectId)", nativeQuery = true)
        int ExistsByProjectId(@Param("projectId") String projectId);

        @Modifying
        @Transactional
        @Query(value = "CALL deleteProjectLookup(:projectId)", nativeQuery = true)
        void deleteProjectLookup(@Param("projectId") String projectId);

        @Modifying
        @Transactional
        @Query(value = "CALL updateProjectLookup(:projectId, :name, :description)", nativeQuery = true)
        void updateProjectLookup(@Param("projectId") String projectId,
                        @Param("name") String name,
                        @Param("description") String description);

}
