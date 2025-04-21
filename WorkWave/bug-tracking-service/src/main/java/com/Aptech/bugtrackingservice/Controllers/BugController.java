package com.Aptech.bugtrackingservice.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.bugtrackingservice.Dtos.Requests.CreateBugRequest;
import com.Aptech.bugtrackingservice.Dtos.Requests.UpdateBugRequestDTO;
import com.Aptech.bugtrackingservice.Dtos.Responses.BugDTO;
import com.Aptech.bugtrackingservice.Dtos.Responses.BugDetailsDTO;
import com.Aptech.bugtrackingservice.Services.Interfaces.BugService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bug")
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;

    @PostMapping
    public ResponseEntity<Void> createBug(@RequestBody CreateBugRequest request) {
        bugService.createBug(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<BugDTO> getBugById(@PathVariable Integer id) {
    // return ResponseEntity.ok(bugService.getBugById(id));
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Void> updateBug(@PathVariable("id") Integer id,
    // @RequestBody UpdateBugRequest request) {
    // bugService.updateBug(id, request);
    // return ResponseEntity.ok().build();
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBug(@PathVariable("id") Integer id) {
        bugService.deleteBug(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/projects/{projectId}/bugs")
    public ResponseEntity<List<BugDetailsDTO>> getBugsByProject(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(bugService.getBugsByProject(projectId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BugDetailsDTO> getBugById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(bugService.getBugDetailsById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBug(
            @PathVariable("id") Integer id,
            @RequestBody UpdateBugRequestDTO request) {
        request.setBugId(id);
        bugService.updateBugWithHistory(request);
        return ResponseEntity.ok().build();
    }

}
