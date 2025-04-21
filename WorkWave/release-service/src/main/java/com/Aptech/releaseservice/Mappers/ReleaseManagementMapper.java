package com.Aptech.releaseservice.Mappers;

import com.Aptech.releaseservice.Dtos.Requests.ReleaseManagementDTO;
import org.mapstruct.Mapper;

import com.Aptech.releaseservice.Dtos.Responses.ReleaseResponseDTO;
import com.Aptech.releaseservice.Entitys.ReleaseManagementEntity;

@Mapper(componentModel = "spring")
public interface ReleaseManagementMapper {

    ReleaseManagementDTO entityToDto(ReleaseManagementEntity entity);

    ReleaseManagementEntity dtoToEntity(ReleaseManagementDTO dto);

    ReleaseResponseDTO entityToresponseDto(ReleaseManagementEntity entity);
    ReleaseManagementEntity dtoresponseToEntity(ReleaseResponseDTO dto);



}
