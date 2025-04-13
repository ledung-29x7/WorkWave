package com.Aptech.projectservice.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.Aptech.projectservice.Dtos.Request.EpicCreationRequest;
import com.Aptech.projectservice.Dtos.Response.EpicResponse;
import com.Aptech.projectservice.Dtos.Response.EpicResponseProjection;
import com.Aptech.projectservice.Entitys.Epic;

@Mapper(componentModel = "Spring")
public interface EpicMapper {
    EpicMapper INSTANCE = Mappers.getMapper(EpicMapper.class);

    @Mapping(target = "epicId", ignore = true) // Bỏ qua epicId khi tạo mới
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Epic toEntity(EpicCreationRequest dto);

    EpicResponse toDto(Epic entity);

    EpicResponse toDto(EpicResponseProjection projection);
}
