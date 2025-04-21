package com.Aptech.testservice.Mappers;

import org.mapstruct.Mapper;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;
import com.Aptech.testservice.Entitys.TestCase;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {
    TestCaseDTO toDTO(TestCase testCase);

    TestCase toEntity(TestCaseDTO testCaseDTO);
}
