package com.Aptech.userservice.Services.Interfaces;

import java.util.List;

import com.Aptech.userservice.Dtos.Request.UserRequest;
import com.Aptech.userservice.Dtos.Request.UserUpdateRequest;
import com.Aptech.userservice.Dtos.Response.PagedUserResponse;
import com.Aptech.userservice.Dtos.Response.UserResponse;

public interface IUserService {
    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(String userId);

    void updateUser(String userId, UserUpdateRequest request);

    void deleteUser(String userId);

    List<UserResponse> searchUsers(String keyword);

    PagedUserResponse searchUsersPaged(String keyword, int pageNumber, int pageSize);

}
