package com.Aptech.releaseservice.Repositorys;


import java.sql.Date;
import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.releaseservice.Dtos.Responses.ReleaseResponseDTO;
import com.Aptech.releaseservice.Entitys.ReleaseManagementEntity;

@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseManagementEntity, Integer> {

        // Gọi Stored Procedure - Tạo mới Release
        @Modifying
        @Transactional
        @Query(value = "CALL sp_create_release(:projectId, :version, :description, :releaseDate, :statusId, :createdBy)", nativeQuery = true)
        void createRelease(
                        @Param("projectId") String projectId,
                        @Param("version") String version,
                        @Param("description") String description,
                        @Param("releaseDate") Date releaseDate,
                        @Param("statusId") Integer statusId,
                        @Param("createdBy") String createdBy);

        // Gọi Stored Procedure - Lấy Release theo ID
        @Query(value = "CALL sp_get_release_by_id(:releaseId)", nativeQuery = true)
        ReleaseResponseDTO getReleaseById(@Param("releaseId") Integer releaseId);

        // Gọi Stored Procedure - Cập nhật Release
        @Modifying
        @Transactional
        @Query(value = "CALL sp_update_release(:releaseId, :version, :description, :releaseDate, :statusId, :updatedBy)", nativeQuery = true)
        void updateRelease(
                        @Param("releaseId") Integer releaseId,
                        @Param("version") String version,
                        @Param("description") String description,
                        @Param("releaseDate") Date releaseDate,
                        @Param("statusId") Integer statusId,
                        @Param("updatedBy") String updatedBy);

        // Gọi Stored Procedure - Xóa Release
        @Modifying
        @Transactional
        @Query(value = "CALL sp_delete_release(:releaseId)", nativeQuery = true)
        void deleteRelease(@Param("releaseId") Integer releaseId);

        // Gọi Stored Procedure - Lấy tất cả Releases của Project
        @Query(value = "CALL sp_get_releases_by_project_id(:projectId)", nativeQuery = true)
        List<ReleaseResponseDTO> getReleasesByProjectId(@Param("projectId") String projectId);
}
