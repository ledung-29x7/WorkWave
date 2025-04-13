package com.Aptech.bugtrackingservice.Mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.Aptech.bugtrackingservice.Dtos.Responses.BugHistoryDTO;
import com.Aptech.bugtrackingservice.Entitys.BugHistoryEntity;

@Mapper(componentModel = "spring")
public interface BugHistoryMapper {

    BugHistoryDTO toDto(BugHistoryEntity entity);

    List<BugHistoryDTO> toDtoList(List<BugHistoryEntity> entities);
}
