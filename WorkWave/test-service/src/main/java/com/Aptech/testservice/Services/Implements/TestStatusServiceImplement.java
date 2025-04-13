package com.Aptech.testservice.Services.Implements;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.testservice.Dtos.Requests.TestStatusDTO;
import com.Aptech.testservice.Mappers.TestStatusMapper;
import com.Aptech.testservice.Repositorys.TestStatusRepository;
import com.Aptech.testservice.Services.Interfaces.TestStatusService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestStatusServiceImplement implements TestStatusService {
    private final TestStatusRepository repository;
    private final TestStatusMapper mapper;

    public List<TestStatusDTO> getAllStatuses() {
        return mapper.toDtoList(repository.findAll());
    }

    public void create(TestStatusDTO dto) {
        repository.save(mapper.toEntity(dto));
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
