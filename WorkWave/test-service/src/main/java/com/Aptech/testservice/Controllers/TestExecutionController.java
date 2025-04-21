package com.Aptech.testservice.Controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.testservice.Dtos.Requests.CreateTestExecutionDTO;
import com.Aptech.testservice.Dtos.Requests.TestExecutionDTO;
import com.Aptech.testservice.Services.Interfaces.TestExecutionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/testexecutions")
@RequiredArgsConstructor
public class TestExecutionController {
    private final TestExecutionService service;

    @PostMapping("/testcases/{testCaseId}/executions")
    public ResponseEntity<Void> create(@PathVariable("testCaseId") Integer testCaseId,
            @RequestBody CreateTestExecutionDTO dto) {
        dto.setTestCaseId(testCaseId);
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/executions/{id}")
    public ResponseEntity<TestExecutionDTO> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/executions/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody CreateTestExecutionDTO dto) {
        service.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/executions/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/testcases/{testCaseId}/executions")
    public ResponseEntity<List<TestExecutionDTO>> getByTestCaseId(@PathVariable("testCaseId") Integer testCaseId) {
        return ResponseEntity.ok(service.getByTestCaseId(testCaseId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TestExecutionDTO>> search(
            @RequestParam String executedBy,
            @RequestParam Integer statusId) {
        return ResponseEntity.ok(service.searchTestExecutions(executedBy, statusId));
    }
}
