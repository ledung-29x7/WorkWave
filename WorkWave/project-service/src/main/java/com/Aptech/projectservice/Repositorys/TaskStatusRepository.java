package com.Aptech.projectservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aptech.projectservice.Entitys.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
    @Query(value = "CALL CreateTaskStatus(:statusName)", nativeQuery = true)
    void createStatus(@Param("statusName") String statusName);

    @Query(value = "CALL GetTaskStatusById(:statusId)", nativeQuery = true)
    TaskStatus getStatusById(@Param("statusId") int statusId);

    @Query(value = "CALL UpdateTaskStatus(:statusId, :statusName)", nativeQuery = true)
    void updateStatus(@Param("statusId") int statusId, @Param("statusName") String statusName);

    @Query(value = "CALL DeleteTaskStatus(:statusId)", nativeQuery = true)
    void deleteStatus(@Param("statusId") int statusId);
}
