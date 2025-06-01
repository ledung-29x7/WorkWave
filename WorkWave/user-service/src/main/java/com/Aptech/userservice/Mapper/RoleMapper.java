package com.Aptech.userservice.Mapper;

import org.mapstruct.Mapper;

import com.Aptech.userservice.Dtos.Response.RoleResponse;
import com.Aptech.userservice.Entitys.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toDto(Role role);
}
