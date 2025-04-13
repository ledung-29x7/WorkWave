package com.Aptech.userservice.Dtos.Response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAllUserResponse {
    Integer totalUsers;
    Integer pageNumber;
    Integer pageSize;
    Integer totalPage;
    List<UserResponse> users;
}
