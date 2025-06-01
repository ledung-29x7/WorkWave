package com.Aptech.userservice.Mapper;

import org.mapstruct.Mapper;

import com.Aptech.userservice.Dtos.Response.PermissionResponse;
import com.Aptech.userservice.Entitys.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toDto(Permission entity);
}
