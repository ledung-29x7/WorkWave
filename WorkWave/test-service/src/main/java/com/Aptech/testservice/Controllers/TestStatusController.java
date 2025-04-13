package com.Aptech.testservice.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.testservice.Dtos.Requests.TestStatusDTO;
import com.Aptech.testservice.Services.Interfaces.TestStatusService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test-status")
@RequiredArgsConstructor
public class TestStatusController {
    private final TestStatusService service;

    @GetMapping
    public List<TestStatusDTO> getAll() {
        return service.getAllStatuses();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TestStatusDTO dto) {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
