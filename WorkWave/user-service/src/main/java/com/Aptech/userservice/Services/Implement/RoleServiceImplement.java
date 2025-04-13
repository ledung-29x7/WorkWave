package com.Aptech.userservice.Services.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.userservice.Dtos.Request.RoleCreationRequest;
import com.Aptech.userservice.Dtos.Request.RoleUpdationRequest;
import com.Aptech.userservice.Dtos.Response.RoleResponse;
import com.Aptech.userservice.Entitys.Role;
import com.Aptech.userservice.Exceptions.AppException;
import com.Aptech.userservice.Exceptions.ErrorCode;
import com.Aptech.userservice.Mapper.RoleMapper;
import com.Aptech.userservice.Repositorys.RoleRepository;
import com.Aptech.userservice.Services.Interfaces.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImplement implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse CreateRole(RoleCreationRequest request) {
        if (roleRepository.existsByRoleName(request.getRoleName()) == 1) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Role role = roleMapper.toRole(request);
        roleRepository.createRole(role.getRoleName());
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> GetAllRole() {
        return roleRepository.GetAllRole();
    }

    @Override
    public RoleResponse GetRoleById(Integer roleId) {
        return roleRepository.GetRoleById(roleId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    public RoleResponse UpdateRole(String roleId, RoleUpdationRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateRole'");
    }

    @Override
    public void DeleteRole(Integer roleId) {
        roleRepository.DeleteRole(roleId);
    }

}
