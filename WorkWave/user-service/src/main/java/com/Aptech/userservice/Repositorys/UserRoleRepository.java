package com.Aptech.userservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Entitys.UserRole;

import jakarta.transaction.Transactional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @Modifying
    @Transactional
    @Query(value = "CALL AssignRole(:userId, :roleId)", nativeQuery = true)
    void AssignRole(
            @Param("userId") String userId,
            @Param("roleId") Integer roleId);
}
