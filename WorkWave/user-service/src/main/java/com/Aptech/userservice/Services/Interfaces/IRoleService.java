package com.Aptech.userservice.Services.Interfaces;

import java.util.List;

import com.Aptech.userservice.Dtos.Request.RoleRequest;
import com.Aptech.userservice.Dtos.Response.RoleResponse;

public interface IRoleService {
    List<RoleResponse> getAll();

    void create(RoleRequest request);

    void update(Integer id, RoleRequest request);

    void delete(Integer id);
}
