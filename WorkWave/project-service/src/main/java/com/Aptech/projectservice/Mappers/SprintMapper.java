package com.Aptech.projectservice.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.Aptech.projectservice.Dtos.Request.SprintRequestDto;
import com.Aptech.projectservice.Dtos.Response.SprintResponseDto;
import com.Aptech.projectservice.Dtos.Response.SprintResponseProjection;
import com.Aptech.projectservice.Entitys.Sprint;

@Mapper(componentModel = "Spring")
public interface SprintMapper {
    @Mapping(target = "sprintId", source = "sprintId")
    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "name", source = "sprintName")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "statusId", source = "status")
    @Mapping(target = "goal", source = "goal")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "updatedBy", source = "updatedBy")
    SprintResponseDto toDto(Sprint sprint);

    @Mapping(target = "sprintId", ignore = true) // Không cần thiết khi tạo mới
    @Mapping(target = "projectId", ignore = true) // Lấy từ request
    @Mapping(target = "createdAt", ignore = true) // Được đặt ở DB
    @Mapping(target = "updatedAt", ignore = true) // Được đặt ở DB
    Sprint toEntity(SprintRequestDto dto);

    SprintResponseDto toDto(SprintResponseProjection projection);
}
