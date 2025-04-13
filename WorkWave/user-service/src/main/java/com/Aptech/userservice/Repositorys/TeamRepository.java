package com.Aptech.userservice.Repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Dtos.Response.TeamResponse;
import com.Aptech.userservice.Entitys.Team;

import jakarta.transaction.Transactional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query(value = "CALL existsByTeamName(:teamName_param)", nativeQuery = true)
    int existsByTeamName(@Param("teamName_param") String teamName_param);

    @Modifying
    @Transactional
    @Query(value = "CALL createTeam(:projectId, :teamName)", nativeQuery = true)
    void createTeam(
            @Param("projectId") String projectId,
            @Param("teamName") String teamName);

    @Query(value = "CALL GetAllTeam()", nativeQuery = true)
    List<TeamResponse> GetAllTeam();

    @Query(value = "CALL GetAllTeamByProjectId(:projectId)", nativeQuery = true)
    List<TeamResponse> GetAllTeamByProjectId(
            @Param("projectId") String projectId);

    @Query(value = "CALL GetTeamById(:teamId)", nativeQuery = true)
    Optional<TeamResponse> GetTeamById(@Param("teamId") Integer teamId);

    @Query(value = "CALL GetTeamByName(:teameName)", nativeQuery = true)
    Optional<TeamResponse> GetTeamByName(@Param("teamName") String teamName);
}
