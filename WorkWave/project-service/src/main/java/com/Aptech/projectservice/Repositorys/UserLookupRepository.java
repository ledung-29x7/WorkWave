package com.Aptech.projectservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aptech.projectservice.Entitys.UserLookup;

import jakarta.transaction.Transactional;

public interface UserLookupRepository extends JpaRepository<UserLookup, String> {
    @Modifying
    @Transactional
    @Query(value = "Call CreateUserLockup(:userId, :name, :email)", nativeQuery = true)
    void CreateUserLockup(@Param("userId") String userId,
            @Param("name") String name,
            @Param("email") String email);

    @Query(value = "CALL ExistsByUserId(:userId)", nativeQuery = true)
    int ExistsByUserId(@Param("userId") String userId);
}
