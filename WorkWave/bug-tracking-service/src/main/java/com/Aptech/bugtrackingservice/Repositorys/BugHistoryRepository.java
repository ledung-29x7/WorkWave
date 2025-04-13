package com.Aptech.bugtrackingservice.Repositorys;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.bugtrackingservice.Entitys.BugHistoryEntity;

@Repository
public interface BugHistoryRepository extends JpaRepository<BugHistoryEntity, Integer> {

    @Query(value = "CALL sp_get_bug_history(:bugId)", nativeQuery = true)
    List<BugHistoryEntity> getHistoryByBugId(@Param("bugId") Integer bugId);

    @Query(value = "CALL sp_get_bug_details_by_id(:bugId)", nativeQuery = true)
    Map<String, Object> getBugDetailsById(@Param("bugId") Integer bugId);
}
