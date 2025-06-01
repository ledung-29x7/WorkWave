package com.Aptech.userservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Entitys.Permission;

import jakarta.transaction.Transactional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Modifying
    @Transactional
    @Query(value = "CALL sp_create_permission(:code, :description)", nativeQuery = true)
    void createPermission(@Param("code") String code, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "CALL sp_update_permission(:id, :description)", nativeQuery = true)
    void updatePermission(@Param("id") Integer id, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "CALL sp_delete_permission(:id)", nativeQuery = true)
    void deletePermission(@Param("id") Integer id);

}
