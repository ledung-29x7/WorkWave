package com.Aptech.bugtrackingservice.Services.Implements;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Aptech.bugtrackingservice.Dtos.Requests.CreateBugRequest;
import com.Aptech.bugtrackingservice.Dtos.Requests.UpdateBugRequest;
import com.Aptech.bugtrackingservice.Dtos.Requests.UpdateBugRequestDTO;
import com.Aptech.bugtrackingservice.Dtos.Responses.BugDTO;
import com.Aptech.bugtrackingservice.Dtos.Responses.BugDetailsDTO;
import com.Aptech.bugtrackingservice.Entitys.Bug;
import com.Aptech.bugtrackingservice.Mappers.BugDetailsMapper;
import com.Aptech.bugtrackingservice.Mappers.BugMapper;
import com.Aptech.bugtrackingservice.Repositorys.BugRepository;
import com.Aptech.bugtrackingservice.Services.Interfaces.BugService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BugServiceImpl implements BugService {

    private final BugRepository bugRepository;
    private final BugMapper bugMapper;
    private final BugDetailsMapper bugDetailsMapper;

    @Override
    public void createBug(CreateBugRequest request) {
        bugRepository.createBug(
                request.getProjectId(),
                request.getTitle(),
                request.getDescription(),
                request.getReportedBy(),
                request.getAssignedTo(),
                request.getSeverityId(),
                request.getPriorityId(),
                request.getStatusId(),
                request.getCreatedBy());
    }

    @Override
    public BugDTO getBugById(Integer id) {
        Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bug not found"));
        return bugMapper.toDTO(bug);
    }

    @Override
    public void updateBug(Integer id, UpdateBugRequest request) {
        bugRepository.updateBug(
                id,
                request.getTitle(),
                request.getDescription(),
                request.getAssignedTo(),
                request.getSeverityId(),
                request.getPriorityId(),
                request.getStatusId(),
                request.getUpdatedBy());
    }

    @Override
    public void deleteBug(Integer id) {
        bugRepository.deleteBug(id);
    }

    @Override
    public List<BugDTO> getBugsByProject(String projectId) {
        return bugRepository.getBugsByProject(projectId)
                .stream()
                .map(bugMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BugDetailsDTO getBugDetailsById(Integer bugId) {
        Map<String, Object> result = bugRepository.getBugDetailsById(bugId);
        return bugDetailsMapper.fromMap(result);
    }

    @Override
    @Transactional
    public void updateBugWithHistory(UpdateBugRequestDTO request) {
        bugRepository.updateBugWithHistory(
                request.getBugId(),
                request.getTitle(),
                request.getDescription(),
                request.getAssignedTo(),
                request.getSeverityId(),
                request.getPriorityId(),
                request.getStatusId(),
                request.getUpdatedBy(),
                request.getComment());
    }

}
