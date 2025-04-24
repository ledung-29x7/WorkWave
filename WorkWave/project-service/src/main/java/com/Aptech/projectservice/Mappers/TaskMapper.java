package com.Aptech.projectservice.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.Aptech.projectservice.Dtos.Response.TaskResponseDto;
import com.Aptech.projectservice.Entitys.Task;

@Mapper(componentModel = "Spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "taskName", target = "name")
    @Mapping(source = "status", target = "statusId")
    TaskResponseDto toDto(Task task);
}
