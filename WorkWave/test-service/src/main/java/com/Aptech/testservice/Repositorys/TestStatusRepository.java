package com.Aptech.testservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Aptech.testservice.Entitys.TestStatus;

@Repository
public interface TestStatusRepository extends JpaRepository<TestStatus, Integer> {
}
