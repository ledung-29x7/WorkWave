package com.Aptech.testservice.Mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Aptech.testservice.Dtos.Requests.TestExecutionDTO;
import com.Aptech.testservice.Entitys.TestExecution;

@Mapper(componentModel = "spring")
public interface TestExecutionMapper {
    TestExecutionDTO toDto(TestExecution entity);

    TestExecution toEntity(TestExecutionDTO dto);

    List<TestExecutionDTO> toDtoList(List<TestExecution> entities);
}
