package com.Aptech.userservice.Mapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.Aptech.userservice.Dtos.Request.UserCreationRequest;
import com.Aptech.userservice.Dtos.Request.UserUpdateRequest;
import com.Aptech.userservice.Dtos.Response.RoleResponse;
import com.Aptech.userservice.Dtos.Response.UserDetailProjection;
import com.Aptech.userservice.Dtos.Response.UserProjection;
import com.Aptech.userservice.Dtos.Response.UserResponse;
import com.Aptech.userservice.Dtos.Response.UserRessponseForLogin;
import com.Aptech.userservice.Dtos.Response.UserRessponseForLoginProjection;
import com.Aptech.userservice.Entitys.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponses(List<User> users);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    @Mapping(target = "roles", expression = "java(mapRoles(userRessponseForLoginProjection.getRoles()))")
    UserRessponseForLogin toUserResponseForLogin(UserRessponseForLoginProjection userRessponseForLoginProjection);

    @Mapping(target = "roles", expression = "java(mapRoles(userDetailProjection.getRoles()))")
    UserResponse toDetailUserResponse(UserDetailProjection userDetailProjection);

    @Mapping(target = "roles", expression = "java(mapRoles(userProjection.getRoles()))")
    UserResponse toAllUserResponse(UserProjection userProjection);

    default Set<RoleResponse> mapRoles(String roles) {
        if (roles == null || roles.isEmpty())
            return new HashSet<>();
        return Arrays.stream(roles.split(","))
                .map(role -> {
                    String[] parts = role.split(":");
                    return new RoleResponse(Integer.parseInt(parts[0]), parts[1]);
                })
                .collect(Collectors.toSet());
    }

}
