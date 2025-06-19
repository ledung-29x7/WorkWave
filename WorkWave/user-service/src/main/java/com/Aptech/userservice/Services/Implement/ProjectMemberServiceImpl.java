package com.Aptech.userservice.Services.Implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.Aptech.userservice.Dtos.Request.ProjectMemberRequest;
import com.Aptech.userservice.Dtos.Response.ProjectMemberResponse;
import com.Aptech.userservice.Entitys.Users;
import com.Aptech.userservice.Exceptions.AppException;
import com.Aptech.userservice.Exceptions.ErrorCode;
import com.Aptech.userservice.Repositorys.ProjectRepository;
import com.Aptech.userservice.Repositorys.ProjectRoleAssignmentRepository;
import com.Aptech.userservice.Repositorys.RoleRepository;
import com.Aptech.userservice.Repositorys.UserRepository;
import com.Aptech.userservice.Services.Interfaces.IProjectMemberService;
import com.Aptech.userservice.event.KafkaProducerService;
import com.aptech.common.event.email.NotificationEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements IProjectMemberService {

    private final ProjectRoleAssignmentRepository repository;
    private final UserRepository userRepository;
    private final KafkaProducerService kafkaProducerService;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<ProjectMemberResponse> getMembers(String projectId) {
        List<Object[]> result = repository.getMembers(projectId);
        return result.stream()
                .map(r -> ProjectMemberResponse.builder()
                        .userId((String) r[0])
                        .userName((String) r[1])
                        .roleId((Integer) r[2])
                        .roleName((String) r[3])
                        .build())
                .toList();
    }

    @Override
    public void assignUser(String projectId, ProjectMemberRequest request) {
        String id = UUID.randomUUID().toString();
        Users user = userRepository.FindByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        repository.assignUser(id, user.getUserId(), projectId, request.getRoleId());

        var project = projectRepository.getById(projectId);
        Map<String, String> data = new HashMap<>();
        data.put("projectName", project.getName());
        data.put("role", roleRepository.getById(request.getRoleId()).getRoleName());

        NotificationEvent event = new NotificationEvent(
                "USER_ASSIGNED",
                request.getEmail(),
                "d-84b65a7f836043f29101eb5985725c89", // template ID
                data);

        kafkaProducerService.send("notification-events", event);

    }

    @Override
    public void updateRole(String projectId, String userId, Integer roleId) {
        repository.updateUserRole(userId, projectId, roleId);
    }

    @Override
    public void removeUser(String projectId, String userId) {
        repository.removeUser(userId, projectId);
    }

    @Override
    public void assignUser(String projectId, String userId, int roleId) {
        String id = UUID.randomUUID().toString();
        repository.assignUser(id, userId, projectId, roleId);
    }
}
