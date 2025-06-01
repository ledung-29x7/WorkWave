package com.Aptech.testservice.Services.Implements;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;
import com.Aptech.testservice.Dtos.Responses.TestCaseResponse;
import com.Aptech.testservice.Mappers.TestCaseMapper;
import com.Aptech.testservice.Repositorys.TestCaseRepository;
import com.Aptech.testservice.Services.Interfaces.TestCaseService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TestCaseServiceImplement implements TestCaseService {
    TestCaseRepository repository;
    TestCaseMapper mapper;

    @Override
    public void createTestCase(TestCaseDTO dto, String createdBy) {
        repository.createTestCase(
                dto.getProjectId(),
                dto.getStoryId(),
                dto.getTestName(),
                dto.getDescription(),
                dto.getExpectedResult(),
                dto.getActualResult(),
                dto.getStatusId(),
                createdBy,
                dto.getExecutedBy());
    }

    @Override
    public TestCaseResponse getTestCaseById(Integer id) {
        return mapper.toDTOrp(repository.getTestCaseById(id));
    }

    @Override
    public void updateTestCase(Integer id, TestCaseDTO dto) {
        repository.updateTestCase(
                id,
                dto.getTestName(),
                dto.getDescription(),
                dto.getExpectedResult(),
                dto.getActualResult(),
                dto.getStatusId(),
                dto.getExecutedBy());
    }

    @Override
    public void deleteTestCase(Integer id) {
        repository.deleteTestCase(id);
    }

    @Override
    public List<TestCaseResponse> getTestCasesByProject(String projectId) {
        return repository.getTestCasesByProject(projectId)
                .stream()
                .map(mapper::toDTOrp)
                .collect(Collectors.toList());
    }
}
