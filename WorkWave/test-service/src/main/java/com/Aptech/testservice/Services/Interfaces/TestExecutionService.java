package com.Aptech.testservice.Services.Interfaces;

import java.util.List;

import com.Aptech.testservice.Dtos.Requests.CreateTestExecutionDTO;
import com.Aptech.testservice.Dtos.Requests.TestExecutionDTO;

public interface TestExecutionService {
    void create(CreateTestExecutionDTO dto);

    TestExecutionDTO getById(Integer id);

    void update(Integer id, CreateTestExecutionDTO dto);

    void delete(Integer id);

    List<TestExecutionDTO> getByTestCaseId(Integer testCaseId);

    List<TestExecutionDTO> searchTestExecutions(String executedBy, Integer statusId);
}
