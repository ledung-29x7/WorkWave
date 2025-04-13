package com.Aptech.bugtrackingservice.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.Aptech.bugtrackingservice.Dtos.Requests.CreateBugRequest;
import com.Aptech.bugtrackingservice.Dtos.Requests.UpdateBugRequest;
import com.Aptech.bugtrackingservice.Dtos.Responses.BugDTO;
import com.Aptech.bugtrackingservice.Entitys.Bug;

@Mapper(componentModel = "spring")
public interface BugMapper {
    BugDTO toDTO(Bug bug);

    Bug toEntity(CreateBugRequest request);

    void updateBugFromRequest(UpdateBugRequest request, @MappingTarget Bug bug);
}
