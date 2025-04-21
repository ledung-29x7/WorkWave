package com.Aptech.testservice.Services.Interfaces;

import java.util.List;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;

public interface TestCaseService {
    void createTestCase(TestCaseDTO testCaseDTO);

    TestCaseDTO getTestCaseById(Integer id);

    void updateTestCase(Integer id, TestCaseDTO testCaseDTO);

    void deleteTestCase(Integer id);

    List<TestCaseDTO> getTestCasesByProject(String projectId);
}
