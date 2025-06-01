package com.Aptech.userservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Entitys.Role;

import jakarta.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Modifying
    @Transactional
    @Query(value = "CALL sp_create_role(:roleName)", nativeQuery = true)
    void createRole(@Param("roleName") String roleName);

    @Modifying
    @Transactional
    @Query(value = "CALL sp_update_role(:roleId, :roleName)", nativeQuery = true)
    void updateRole(@Param("roleId") Integer roleId, @Param("roleName") String roleName);

    @Modifying
    @Transactional
    @Query(value = "CALL sp_delete_role(:roleId)", nativeQuery = true)
    void deleteRole(@Param("roleId") Integer roleId);
}
