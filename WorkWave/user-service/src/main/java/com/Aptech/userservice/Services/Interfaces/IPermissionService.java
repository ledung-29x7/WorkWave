package com.Aptech.userservice.Services.Interfaces;

import java.util.List;

import com.Aptech.userservice.Dtos.Request.PermissionRequest;
import com.Aptech.userservice.Dtos.Response.PermissionResponse;

public interface IPermissionService {
    List<PermissionResponse> getAll();

    void create(PermissionRequest request);

    void update(Integer id, PermissionRequest request);

    void delete(Integer id);
}
