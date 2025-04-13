package com.Aptech.bugtrackingservice.Services.Interfaces;

import java.util.List;

import com.Aptech.bugtrackingservice.Dtos.Responses.BugHistoryDTO;

public interface BugHistoryService {
    List<BugHistoryDTO> getHistoryByBugId(Integer bugId);
}
