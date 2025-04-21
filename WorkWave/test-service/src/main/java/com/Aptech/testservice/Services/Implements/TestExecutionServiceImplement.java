package com.Aptech.testservice.Services.Implements;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Aptech.testservice.Dtos.Requests.CreateTestExecutionDTO;
import com.Aptech.testservice.Dtos.Requests.TestExecutionDTO;
import com.Aptech.testservice.Mappers.TestExecutionMapper;
import com.Aptech.testservice.Repositorys.TestExecutionRepository;
import com.Aptech.testservice.Services.Interfaces.TestExecutionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TestExecutionServiceImplement implements TestExecutionService {
    TestExecutionRepository repository;
    TestExecutionMapper mapper;

    @Override
    public void create(CreateTestExecutionDTO dto) {
        repository.createTestExecution(dto.getTestCaseId(), dto.getExecutedBy(), dto.getStatusId(), dto.getComment());
    }

    @Override
    public TestExecutionDTO getById(Integer id) {
        return mapper.toDTO(repository.findExecutionById(id));
    }

    @Override
    public void update(Integer id, CreateTestExecutionDTO dto) {
        repository.updateExecution(id, dto.getExecutedBy(), dto.getStatusId(), dto.getComment());
    }

    @Override
    public void delete(Integer id) {
        repository.deleteExecution(id);
    }

    @Override
    public List<TestExecutionDTO> getByTestCaseId(Integer testCaseId) {
        return repository.findAllByTestCase(testCaseId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TestExecutionDTO> searchTestExecutions(String executedBy, Integer statusId) {
        return repository.searchTestExecutions(executedBy, statusId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
