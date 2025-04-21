package com.Aptech.testservice.Repositorys;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.testservice.Entitys.TestCase;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Integer> {
        @Modifying
        @Transactional
        @Query(value = "CALL sp_create_test_case(:projectId, :storyId, :testName, :description, :expectedResult, :actualResult, :statusId, :createdBy, :executedBy)", nativeQuery = true)
        void createTestCase(
                        @Param("projectId") String projectId,
                        @Param("storyId") Integer storyId,
                        @Param("testName") String testName,
                        @Param("description") String description,
                        @Param("expectedResult") String expectedResult,
                        @Param("actualResult") String actualResult,
                        @Param("statusId") Integer statusId,
                        @Param("createdBy") String createdBy,
                        @Param("executedBy") String executedBy);

        @Query(value = "CALL sp_get_test_case_by_id(:id)", nativeQuery = true)
        TestCase getTestCaseById(@Param("id") Integer id);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_update_test_case(:id, :testName, :description, :expectedResult, :actualResult, :statusId, :executedBy)", nativeQuery = true)
        void updateTestCase(
                        @Param("id") Integer id,
                        @Param("testName") String testName,
                        @Param("description") String description,
                        @Param("expectedResult") String expectedResult,
                        @Param("actualResult") String actualResult,
                        @Param("statusId") Integer statusId,
                        @Param("executedBy") String executedBy);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_delete_test_case(:id)", nativeQuery = true)
        void deleteTestCase(@Param("id") Integer id);

        @Query(value = "CALL sp_get_test_cases_by_project(:projectId)", nativeQuery = true)
        List<TestCase> getTestCasesByProject(@Param("projectId") String projectId);
}
