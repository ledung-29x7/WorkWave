package com.Aptech.testservice.Controllers;

import java.util.List;

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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test-cases")
@RequiredArgsConstructor
public class TestCaseController {
    private final TestCaseService service;

    @GetMapping
    public List<TestCaseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public TestCaseDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public TestCaseDTO create(@RequestBody TestCaseDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public TestCaseDTO update(@PathVariable Integer id, @RequestBody TestCaseDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
