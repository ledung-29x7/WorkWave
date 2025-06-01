package com.Aptech.userservice.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Entitys.ProjectRoleAssignment;

import jakarta.transaction.Transactional;

@Repository
public interface ProjectRoleAssignmentRepository extends JpaRepository<ProjectRoleAssignment, String> {

        @Modifying
        @Transactional
        @Query(value = "CALL sp_assign_user_to_project(:assignmentId, :userId, :projectId, :roleId)", nativeQuery = true)
        void assignUser(@Param("assignmentId") String assignmentId,
                        @Param("userId") String userId,
                        @Param("projectId") String projectId,
                        @Param("roleId") Integer roleId);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_assign_user_to_project(:assignmentId, :userId, :projectId, :roleId)", nativeQuery = true)
        void assignUser(@Param("assignmentId") String assignmentId,
                        @Param("userId") String userId,
                        @Param("projectId") String projectId,
                        @Param("roleId") int roleId);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_update_user_role_in_project(:userId, :projectId, :roleId)", nativeQuery = true)
        void updateUserRole(@Param("userId") String userId,
                        @Param("projectId") String projectId,
                        @Param("roleId") Integer roleId);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_remove_user_from_project(:userId, :projectId)", nativeQuery = true)
        void removeUser(@Param("userId") String userId,
                        @Param("projectId") String projectId);

        @Query(value = "CALL sp_get_project_members(:projectId)", nativeQuery = true)
        List<Object[]> getMembers(@Param("projectId") String projectId);
}
