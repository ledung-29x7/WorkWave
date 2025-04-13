package com.Aptech.userservice.Repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Dtos.Response.RoleResponse;
import com.Aptech.userservice.Entitys.Role;

import jakarta.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "CALL existsByRoleName(:roleName_param)", nativeQuery = true)
    int existsByRoleName(@Param("roleName_param") String roleName_param);

    @Modifying
    @Transactional
    @Query(value = "CALL createRole(:roleName)", nativeQuery = true)
    void createRole(
            @Param("roleName") String roleName);

    @Query(value = "CALL GetAllRole()", nativeQuery = true)
    List<RoleResponse> GetAllRole();

    @Query(value = "CALL GetRoleById(:roleId)", nativeQuery = true)
    Optional<RoleResponse> GetRoleById(@Param("roleId") Integer roleId);

    @Query(value = "CALL DeleteRole(:roleId)", nativeQuery = true)
    void DeleteRole(@Param("roleId") Integer roleId);

    @Query(value = "CALL GetRoleRoleName(:roleName)", nativeQuery = true)
    Optional<RoleResponse> GetRoleByRoleName(@Param("roleName") String roleName);
}
