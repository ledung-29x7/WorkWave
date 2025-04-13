package com.Aptech.projectservice.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.Aptech.projectservice.Dtos.Request.UserStoryRequestDto;
import com.Aptech.projectservice.Dtos.Response.UserStoryResponseDto;
import com.Aptech.projectservice.Entitys.UserStory;

@Mapper(componentModel = "Spring")
public interface UserStoryMapper {
    @Mapping(target = "storyId", source = "userStoryId")
    @Mapping(target = "epicId", source = "epicId")
    @Mapping(target = "sprintId", source = "sprintId")
    @Mapping(target = "name", source = "userStoryName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "priorityId", source = "priority")
    @Mapping(target = "statusId", source = "status")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "updatedBy", source = "updatedBy")
    UserStoryResponseDto toDto(UserStory userStory);

    @Mapping(target = "userStoryId", ignore = true)
    @Mapping(target = "epicId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserStory toEntity(UserStoryRequestDto dto);
}
