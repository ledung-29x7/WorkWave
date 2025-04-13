package com.Aptech.userservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Entitys.TeamMember;

import jakarta.transaction.Transactional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    @Modifying
    @Transactional
    @Query(value = "CALL AssignTeam(:teamId, :userId)", nativeQuery = true)
    void AssignTeam(
            @Param("teamId") Integer teamId,
            @Param("userId") String userId);
}
