package com.Aptech.testservice.Services.Interfaces;

import java.util.List;

import com.Aptech.testservice.Dtos.Requests.TestStatusDTO;

public interface TestStatusService {
    public List<TestStatusDTO> getAllStatuses();

    public void create(TestStatusDTO dto);

    public void delete(Integer id);
}
