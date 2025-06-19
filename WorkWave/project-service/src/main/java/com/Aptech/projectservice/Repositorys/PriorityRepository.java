package com.Aptech.projectservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aptech.projectservice.Entitys.Priority;

public interface PriorityRepository extends JpaRepository<Priority, Integer> {

    @Query(value = "CALL CreatePriority(:priorityName)", nativeQuery = true)
    void createPriority(@Param("priorityName") String priorityName);

    @Query(value = "CALL GetPriorityById(:priorityId)", nativeQuery = true)
    Priority getPriorityById(@Param("priorityId") int priorityId);

    @Query(value = "CALL UpdatePriority(:priorityId, :priorityName)", nativeQuery = true)
    void updatePriority(@Param("priorityId") int priorityId,
            @Param("priorityName") String priorityName);

    @Query(value = "CALL DeletePriority(:priorityId)", nativeQuery = true)
    void deletePriority(@Param("priorityId") int priorityId);
}
