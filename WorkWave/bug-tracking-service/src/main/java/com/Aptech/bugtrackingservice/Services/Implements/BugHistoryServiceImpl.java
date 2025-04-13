package com.Aptech.bugtrackingservice.Services.Implements;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Aptech.bugtrackingservice.Dtos.Responses.BugHistoryDTO;
import com.Aptech.bugtrackingservice.Entitys.BugHistoryEntity;
import com.Aptech.bugtrackingservice.Mappers.BugHistoryMapper;
import com.Aptech.bugtrackingservice.Repositorys.BugHistoryRepository;
import com.Aptech.bugtrackingservice.Services.Interfaces.BugHistoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BugHistoryServiceImpl implements BugHistoryService {

    private final BugHistoryRepository bugHistoryRepository;
    private final BugHistoryMapper bugHistoryMapper;

    @Override
    public List<BugHistoryDTO> getHistoryByBugId(Integer bugId) {
        List<BugHistoryEntity> entities = bugHistoryRepository.getHistoryByBugId(bugId);
        return bugHistoryMapper.toDtoList(entities);
    }

}
