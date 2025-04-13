package com.Aptech.testservice.Mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;
import com.Aptech.testservice.Entitys.TestCase;

@Mapper(componentModel = "spring")
public interface TestCaseMapper {
    TestCaseDTO toDto(TestCase entity);

    TestCase toEntity(TestCaseDTO dto);

    List<TestCaseDTO> toDtoList(List<TestCase> entities);
}
