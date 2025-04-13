package com.Aptech.testservice.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.testservice.Entitys.TestExecution;

@Repository
public interface TestExecutionRepository extends JpaRepository<TestExecution, Integer> {
    List<TestExecution> findByTestCaseId(Integer testCaseId);

    @Query(value = "CALL SearchTestExecutions(:executedBy, :statusId)", nativeQuery = true)
    List<TestExecution> search(@Param("executedBy") String executedBy, @Param("statusId") Integer statusId);

    @Query(value = "CALL GetExecutionSummary(:projectId)", nativeQuery = true)
    Object getSummary(@Param("projectId") String projectId);
}
