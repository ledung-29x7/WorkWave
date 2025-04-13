package com.Aptech.userservice.Dtos.Response;

public interface UserProjection {
    String getUserId();

    String getUserName();

    String getEmail();

    int getTotalUsers();

    int getTotalPage();

    String getRoles();
}
