package com.Aptech.testservice.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.testservice.Dtos.Requests.TestExecutionDTO;
import com.Aptech.testservice.Services.Interfaces.TestExecutionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test-executions")
@RequiredArgsConstructor
public class TestExecutionController {
    private final TestExecutionService service;

    @GetMapping
    public List<TestExecutionDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<TestExecutionDTO> search(@RequestParam(required = false) String executedBy,
            @RequestParam(required = false) Integer statusId) {
        return service.search(executedBy, statusId);
    }

    @GetMapping("/summary")
    public Object getSummary(@RequestParam String projectId) {
        return service.getSummary(projectId);
    }

    @PostMapping
    public TestExecutionDTO create(@RequestBody TestExecutionDTO dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
