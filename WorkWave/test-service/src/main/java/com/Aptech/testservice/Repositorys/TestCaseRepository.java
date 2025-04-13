package com.Aptech.testservice.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Aptech.testservice.Entitys.TestCase;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Integer> {
    List<TestCase> findByProjectId(String projectId);

    List<TestCase> findByStoryId(Integer storyId);
}
