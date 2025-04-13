package com.Aptech.testservice.Services.Interfaces;

import java.util.List;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;

public interface TestCaseService {
    public List<TestCaseDTO> getAll();

    public TestCaseDTO getById(Integer id);

    public TestCaseDTO create(TestCaseDTO dto);

    public TestCaseDTO update(Integer id, TestCaseDTO dto);

    public void delete(Integer id);
}
