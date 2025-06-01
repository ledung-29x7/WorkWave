package com.Aptech.userservice.Services.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.userservice.Dtos.Request.PermissionRequest;
import com.Aptech.userservice.Dtos.Response.PermissionResponse;
import com.Aptech.userservice.Exceptions.AppException;
import com.Aptech.userservice.Exceptions.ErrorCode;
import com.Aptech.userservice.Mapper.PermissionMapper;
import com.Aptech.userservice.Repositorys.PermissionRepository;
import com.Aptech.userservice.Services.Interfaces.IPermissionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {

    private final PermissionRepository permissionRepo;
    private final PermissionMapper permissionMapper;

    @Override
    public List<PermissionResponse> getAll() {
        return permissionRepo.findAll().stream()
                .map(permissionMapper::toDto)
                .toList();
    }

    @Override
    public void create(PermissionRequest request) {
        permissionRepo.createPermission(request.getCode(), request.getDescription());
    }

    @Override
    public void update(Integer id, PermissionRequest request) {
        permissionRepo.updatePermission(id, request.getDescription());
    }

    @Override
    public void delete(Integer id) {
        try {
            permissionRepo.deletePermission(id);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
