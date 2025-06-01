package com.Aptech.userservice.Services.Implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.userservice.Dtos.Request.UserRequest;
import com.Aptech.userservice.Dtos.Request.UserUpdateRequest;
import com.Aptech.userservice.Dtos.Response.PagedUserResponse;
import com.Aptech.userservice.Dtos.Response.UserResponse;
import com.Aptech.userservice.Entitys.Users;
import com.Aptech.userservice.Mapper.UserMapper;
import com.Aptech.userservice.Repositorys.UserRepository;
import com.Aptech.userservice.Services.Interfaces.IUserService;
import com.Aptech.userservice.event.KafkaProducerService;
import com.aptech.common.event.user.UserCreatedEvent;
import com.aptech.common.event.user.UserDeletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public UserResponse createUser(UserRequest request) {
        // Gọi SP và nhận userId từ OUT parameter
        String userId = userRepository.createUserByProcedure(
                request.getUserName(),
                request.getEmail(),
                request.getPassword());
        log.info("User ID returned from SP: {}", userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Gửi event Kafka
        UserCreatedEvent event = new UserCreatedEvent(
                userId,
                request.getEmail(),
                request.getUserName());
        kafkaProducerService.send("user-events", event);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserResponse getUserById(String userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not Found"));
        return userMapper.toDto(user);
    }

    @Override
    public void updateUser(String userId, UserUpdateRequest request) {
        userRepository.updateUserByProcedure(userId, request.getUserName(), request.getEmail());
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteUserByProcedure(userId);
        UserDeletedEvent event = new UserDeletedEvent(userId);
        kafkaProducerService.send("user-events", event);
    }

    @Override
    public List<UserResponse> searchUsers(String keyword) {
        List<Users> users = userRepository.searchUsersByProcedure(keyword);
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public PagedUserResponse searchUsersPaged(String keyword, int pageNumber, int pageSize) {
        List<Object[]> rows = userRepository.searchUsersPaged(keyword, pageNumber, pageSize);

        List<UserResponse> users = new ArrayList<>();
        int totalPages = 0, pageNum = 0, pageSz = 0;
        long totalItems = 0;

        for (Object[] row : rows) {
            users.add(UserResponse.builder()
                    .userId((String) row[0])
                    .userName((String) row[1])
                    .email((String) row[2])
                    .isActive((Boolean) row[3])
                    .build());

            // Lấy các thông tin phân trang (chỉ cần lấy từ dòng đầu tiên)
            if (totalItems == 0) {
                totalItems = ((Number) row[4]).longValue();
                pageNum = ((Number) row[5]).intValue();
                pageSz = ((Number) row[6]).intValue();
                totalPages = ((Number) row[7]).intValue();
            }
        }

        return PagedUserResponse.builder()
                .users(users)
                .pageNumber(pageNum)
                .pageSize(pageSz)
                .totalPages(totalPages)
                .totalItems(totalItems)
                .build();
    }

}
