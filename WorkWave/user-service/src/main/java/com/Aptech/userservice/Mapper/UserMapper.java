package com.Aptech.userservice.Mapper;

import org.mapstruct.Mapper;

import com.Aptech.userservice.Dtos.Request.UserRequest;
import com.Aptech.userservice.Dtos.Response.UserResponse;
import com.Aptech.userservice.Entitys.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toEntity(UserRequest dto);

    UserResponse toDto(Users user);
}
