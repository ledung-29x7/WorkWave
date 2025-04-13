package com.Aptech.testservice.Services.Implements;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.testservice.Dtos.Requests.TestExecutionDTO;
import com.Aptech.testservice.Entitys.TestExecution;
import com.Aptech.testservice.Mappers.TestExecutionMapper;
import com.Aptech.testservice.Repositorys.TestExecutionRepository;
import com.Aptech.testservice.Services.Interfaces.TestExecutionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestExecutionServiceImplement implements TestExecutionService {
    private final TestExecutionRepository repository;
    private final TestExecutionMapper mapper;

    public List<TestExecutionDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    public List<TestExecutionDTO> search(String executedBy, Integer statusId) {
        return mapper.toDtoList(repository.search(executedBy, statusId));
    }

    public Object getSummary(String projectId) {
        return repository.getSummary(projectId);
    }

    public TestExecutionDTO create(TestExecutionDTO dto) {
        TestExecution entity = mapper.toEntity(dto);
        entity.setExecutionDate(LocalDateTime.now());
        return mapper.toDto(repository.save(entity));
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
