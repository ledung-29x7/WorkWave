package com.Aptech.testservice.Services.Interfaces;

import java.util.List;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;
import com.Aptech.testservice.Dtos.Responses.TestCaseResponse;

public interface TestCaseService {
    void createTestCase(TestCaseDTO testCaseDTO, String createdBy);

    TestCaseResponse getTestCaseById(Integer id);

    void updateTestCase(Integer id, TestCaseDTO testCaseDTO);

    void deleteTestCase(Integer id);

    List<TestCaseResponse> getTestCasesByProject(String projectId);
}
