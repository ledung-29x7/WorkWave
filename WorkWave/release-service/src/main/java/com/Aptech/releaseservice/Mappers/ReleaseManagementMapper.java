package com.Aptech.releaseservice.Mappers;

import org.mapstruct.Mapper;

import com.Aptech.releaseservice.Dtos.Responses.ReleaseManagementDTO;
import com.Aptech.releaseservice.Entitys.ReleaseManagementEntity;

@Mapper(componentModel = "spring")
public interface ReleaseManagementMapper {

    ReleaseManagementDTO entityToDto(ReleaseManagementEntity entity);

    ReleaseManagementEntity dtoToEntity(ReleaseManagementDTO dto);
}
