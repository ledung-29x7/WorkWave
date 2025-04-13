package com.Aptech.bugtrackingservice.Services.Interfaces;

import java.util.List;

import com.Aptech.bugtrackingservice.Dtos.Requests.CreateBugRequest;
import com.Aptech.bugtrackingservice.Dtos.Requests.UpdateBugRequest;
import com.Aptech.bugtrackingservice.Dtos.Requests.UpdateBugRequestDTO;
import com.Aptech.bugtrackingservice.Dtos.Responses.BugDTO;
import com.Aptech.bugtrackingservice.Dtos.Responses.BugDetailsDTO;

public interface BugService {
    void createBug(CreateBugRequest request);

    BugDTO getBugById(Integer id);

    void updateBug(Integer id, UpdateBugRequest request);

    void deleteBug(Integer id);

    List<BugDTO> getBugsByProject(String projectId);

    BugDetailsDTO getBugDetailsById(Integer bugId);

    public void updateBugWithHistory(UpdateBugRequestDTO request);
}
