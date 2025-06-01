package com.Aptech.userservice.Services.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.userservice.Dtos.Response.RolePermissionResponse;
import com.Aptech.userservice.Repositorys.RolePermissionRepository;
import com.Aptech.userservice.Services.Interfaces.IRolePermissionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl implements IRolePermissionService {

    private final RolePermissionRepository repository;

    @Override
    public List<RolePermissionResponse> getPermissionsByRole(Integer roleId) {
        List<Object[]> rows = repository.getPermissionsByRole(roleId);
        return rows.stream()
                .map(r -> RolePermissionResponse.builder()
                        .permissionId((Integer) r[0])
                        .code((String) r[1])
                        .description((String) r[2])
                        .build())
                .toList();
    }

    @Override
    public void addPermission(Integer roleId, Integer permissionId) {
        repository.addPermission(roleId, permissionId);
    }

    @Override
    public void removePermission(Integer roleId, Integer permissionId) {
        repository.removePermission(roleId, permissionId);
    }
}
