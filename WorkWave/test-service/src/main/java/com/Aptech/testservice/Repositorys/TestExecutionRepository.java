package com.Aptech.testservice.Repositorys;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.testservice.Entitys.TestExecution;

@Repository
public interface TestExecutionRepository extends JpaRepository<TestExecution, Integer> {
        @Modifying
        @Transactional
        @Query(value = "CALL sp_create_test_execution(:testCaseId, :executedBy, :statusId, :comment)", nativeQuery = true)
        void createTestExecution(
                        @Param("testCaseId") Integer testCaseId,
                        @Param("executedBy") String executedBy,
                        @Param("statusId") Integer statusId,
                        @Param("comment") String comment);

        @Query(value = "CALL sp_get_test_execution_by_id(:id)", nativeQuery = true)
        TestExecution findExecutionById(@Param("id") Integer id);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_update_test_execution(:id, :executedBy, :statusId, :comment)", nativeQuery = true)
        void updateExecution(
                        @Param("id") Integer id,
                        @Param("executedBy") String executedBy,
                        @Param("statusId") Integer statusId,
                        @Param("comment") String comment);

        @Modifying
        @Query(value = "CALL sp_delete_test_execution(:id)", nativeQuery = true)
        void deleteExecution(@Param("id") Integer id);

        @Query(value = "CALL sp_get_executions_by_testcase(:testCaseId)", nativeQuery = true)
        List<TestExecution> findAllByTestCase(@Param("testCaseId") Integer testCaseId);

        @Query(value = "CALL SearchTestExecutions(:executedBy, :statusId)", nativeQuery = true)
        List<TestExecution> searchTestExecutions(@Param("executedBy") String executedBy,
                        @Param("statusId") Integer statusId);
}
