package com.Aptech.testservice.Services.Implements;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;
import com.Aptech.testservice.Entitys.TestCase;
import com.Aptech.testservice.Mappers.TestCaseMapper;
import com.Aptech.testservice.Repositorys.TestCaseRepository;
import com.Aptech.testservice.Services.Interfaces.TestCaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImplement implements TestCaseService {
    private final TestCaseRepository repository;
    private final TestCaseMapper mapper;

    public List<TestCaseDTO> getAll() {
        return mapper.toDtoList(repository.findAll());
    }

    public TestCaseDTO getById(Integer id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("TestCase not found"));
    }

    public TestCaseDTO create(TestCaseDTO dto) {
        TestCase entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repository.save(entity));
    }

    public TestCaseDTO update(Integer id, TestCaseDTO dto) {
        TestCase entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestCase not found"));

        entity.setTestName(dto.getTestName());
        entity.setDescription(dto.getDescription());
        entity.setExpectedResult(dto.getExpectedResult());
        entity.setActualResult(dto.getActualResult());
        entity.setStatusId(dto.getStatusId());
        entity.setExecutedBy(dto.getExecutedBy());
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDto(repository.save(entity));
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
