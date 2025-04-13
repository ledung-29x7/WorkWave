package com.Aptech.testservice.Services.Interfaces;

import java.util.List;

import com.Aptech.testservice.Dtos.Requests.TestExecutionDTO;

public interface TestExecutionService {
    public List<TestExecutionDTO> getAll();

    public List<TestExecutionDTO> search(String executedBy, Integer statusId);

    public Object getSummary(String projectId);

    public TestExecutionDTO create(TestExecutionDTO dto);

    public void delete(Integer id);
}
