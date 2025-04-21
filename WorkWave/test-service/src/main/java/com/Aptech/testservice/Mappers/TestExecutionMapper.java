package com.Aptech.testservice.Mappers;

import org.mapstruct.Mapper;

import com.Aptech.testservice.Dtos.Requests.CreateTestExecutionDTO;
import com.Aptech.testservice.Dtos.Requests.TestExecutionDTO;
import com.Aptech.testservice.Entitys.TestExecution;

@Mapper(componentModel = "spring")
public interface TestExecutionMapper {
    TestExecutionDTO toDTO(TestExecution testExecution);

    TestExecution toEntity(CreateTestExecutionDTO dto);

}
