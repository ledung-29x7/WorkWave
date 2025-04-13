package com.Aptech.userservice.Services.Implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Aptech.userservice.Dtos.Request.AssignTeamRequest;
import com.Aptech.userservice.Dtos.Request.UserCreationRequest;
import com.Aptech.userservice.Dtos.Request.UserRoleCreationRequest;
import com.Aptech.userservice.Dtos.Response.GetAllUserResponse;
import com.Aptech.userservice.Dtos.Response.UserDetailProjection;
import com.Aptech.userservice.Dtos.Response.UserProjection;
import com.Aptech.userservice.Dtos.Response.UserResponse;
import com.Aptech.userservice.Entitys.User;
import com.Aptech.userservice.Exceptions.AppException;
import com.Aptech.userservice.Exceptions.ErrorCode;
import com.Aptech.userservice.Mapper.UserMapper;
import com.Aptech.userservice.Repositorys.TeamMemberRepository;
import com.Aptech.userservice.Repositorys.UserRepository;
import com.Aptech.userservice.Repositorys.UserRoleRepository;
import com.Aptech.userservice.Services.Interfaces.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImplement implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    UserRoleRepository userRoleRepository;
    TeamMemberRepository teamMemberRepository;

    @Override
    public UserResponse CreateUser(UserCreationRequest request) {
        if (userRepository.ExistsByUserName(request.getUserName()) == 1) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.CreateUser(user.getUserName(), user.getEmail(), user.getPassword());

        return userMapper.toUserResponse(user);

    }

    @Override
    // @PreAuthorize("hasRole('Tester')")
    public GetAllUserResponse getAllUsers(int pageNumber, int pageSize, String searchName, String searchEmail) {
        List<UserProjection> userProjections = userRepository.getAllUsers(pageNumber, pageSize, searchName,
                searchEmail);
        if (userProjections.isEmpty()) {
            return GetAllUserResponse.builder()
                    .totalUsers(0)
                    .pageNumber(pageNumber)
                    .pageSize(pageSize)
                    .totalPage(0)
                    .users(List.of())
                    .build();
        }
        int totalUsers = userProjections.get(0).getTotalUsers();
        int totalPage = userProjections.get(0).getTotalPage();
        List<UserResponse> users = userProjections.stream()
                .map(userMapper::toAllUserResponse)
                .collect(Collectors.toList());

        return GetAllUserResponse.builder()
                .totalUsers(totalUsers)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPage(totalPage)
                .users(users)
                .build();
    }

    @Override
    public void DeleteUser(String userId) {
        userRepository.DeleteUser(userId);
    }

    @Override
    public void AssignRole(UserRoleCreationRequest request) {
        userRoleRepository.AssignRole(request.getUserId(), request.getRoleId());
    }

    @Override
    public UserResponse getUserById(String userId) {
        UserDetailProjection userProjection = userRepository.getUserById(userId);
        if (userProjection == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        return userMapper.toDetailUserResponse(userProjection);
    }

    @Override
    public void AssignTeam(AssignTeamRequest request) {
        teamMemberRepository.AssignTeam(request.getTeamId(), request.getUserId());
    }

}
