package com.Aptech.testservice.Mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Aptech.testservice.Dtos.Requests.TestStatusDTO;
import com.Aptech.testservice.Entitys.TestStatus;

@Mapper(componentModel = "spring")
public interface TestStatusMapper {
    TestStatusDTO toDto(TestStatus entity);

    TestStatus toEntity(TestStatusDTO dto);

    List<TestStatusDTO> toDtoList(List<TestStatus> entities);
}