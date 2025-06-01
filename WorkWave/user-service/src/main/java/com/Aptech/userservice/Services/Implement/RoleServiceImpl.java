package com.Aptech.userservice.Services.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.userservice.Dtos.Request.RoleRequest;
import com.Aptech.userservice.Dtos.Response.RoleResponse;
import com.Aptech.userservice.Exceptions.AppException;
import com.Aptech.userservice.Exceptions.ErrorCode;
import com.Aptech.userservice.Mapper.RoleMapper;
import com.Aptech.userservice.Repositorys.RoleRepository;
import com.Aptech.userservice.Services.Interfaces.IRoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepo;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getAll() {
        return roleRepo.findAll().stream()
                .map(roleMapper::toDto)
                .toList();
    }

    @Override
    public void create(RoleRequest request) {
        roleRepo.createRole(request.getRoleName());
    }

    @Override
    public void update(Integer id, RoleRequest request) {
        roleRepo.updateRole(id, request.getRoleName());
    }

    @Override
    public void delete(Integer id) {
        try {
            roleRepo.deleteRole(id);
        } catch (Exception e) {
            throw new AppException(ErrorCode.USER_ALREADY_HAS_THIS_ROLE); // hoặc lỗi phù hợp hơn
        }
    }
}
