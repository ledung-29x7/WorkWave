package com.Aptech.projectservice.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.Aptech.projectservice.Dtos.Response.TaskResponseDto;
import com.Aptech.projectservice.Entitys.Task;

@Mapper(componentModel = "Spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskResponseDto toDto(Task task);
}
