package com.Aptech.userservice.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aptech.userservice.Entitys.UserGlobalRole;
import com.Aptech.userservice.Entitys.UserGlobalRoleId;

public interface UserGlobalRoleRepository extends JpaRepository<UserGlobalRole, UserGlobalRoleId> {
    @Query(value = "CALL sp_get_permission_codes_by_user_global(:userId)", nativeQuery = true)
    List<String> getGlobalPermissionCodesByUserId(@Param("userId") String userId);
}
