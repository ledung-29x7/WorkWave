package com.Aptech.userservice.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Entitys.RolePermission;
import com.Aptech.userservice.Entitys.RolePermissionId;

import jakarta.transaction.Transactional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {

    @Modifying
    @Transactional
    @Query(value = "CALL sp_add_permission_to_role(:roleId, :permissionId)", nativeQuery = true)
    void addPermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    @Modifying
    @Transactional
    @Query(value = "CALL sp_remove_permission_from_role(:roleId, :permissionId)", nativeQuery = true)
    void removePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    @Query(value = "CALL sp_get_permissions_by_role(:roleId)", nativeQuery = true)
    List<Object[]> getPermissionsByRole(@Param("roleId") Integer roleId);
}
