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
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.testservice.Dtos.Requests.TestCaseDTO;
import com.Aptech.testservice.Services.Interfaces.TestCaseService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/testcases")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class TestCaseController {
    private final TestCaseService service;

    @PostMapping
    public ResponseEntity<Void> createTestCase(@RequestBody TestCaseDTO dto) {
        service.createTestCase(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCaseDTO> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getTestCaseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTestCase(@PathVariable("id") Integer id, @RequestBody TestCaseDTO dto) {
        service.updateTestCase(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable("id") Integer id) {
        service.deleteTestCase(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TestCaseDTO>> getByProject(@PathVariable("projectId") String projectId) {
        return ResponseEntity.ok(service.getTestCasesByProject(projectId));
    }
}
