package com.Aptech.userservice.Dtos.Response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedUserResponse {
    private List<UserResponse> users;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalItems;
}
