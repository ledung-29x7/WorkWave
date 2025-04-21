package com.Aptech.testservice.Services.Implements;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;
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
    public void createTestCase(TestCaseDTO dto) {
        repository.createTestCase(
                dto.getProjectId(),
                dto.getStoryId(),
                dto.getTestName(),
                dto.getDescription(),
                dto.getExpectedResult(),
                dto.getActualResult(),
                dto.getStatusId(),
                dto.getCreatedBy(),
                dto.getExecutedBy());
    }

    @Override
    public TestCaseDTO getTestCaseById(Integer id) {
        return mapper.toDTO(repository.getTestCaseById(id));
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
    public List<TestCaseDTO> getTestCasesByProject(String projectId) {
        return repository.getTestCasesByProject(projectId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
