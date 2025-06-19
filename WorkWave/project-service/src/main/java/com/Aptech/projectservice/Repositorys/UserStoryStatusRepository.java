package com.Aptech.projectservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Aptech.projectservice.Entitys.UserStoryStatus;

public interface UserStoryStatusRepository extends JpaRepository<UserStoryStatus, Integer> {
    @Query(value = "CALL CreateUserStoryStatus(:statusName)", nativeQuery = true)
    void createStatus(@Param("statusName") String statusName);

    @Query(value = "CALL GetUserStoryStatusById(:statusId)", nativeQuery = true)
    UserStoryStatus getStatusById(@Param("statusId") int statusId);

    @Query(value = "CALL UpdateUserStoryStatus(:statusId, :statusName)", nativeQuery = true)
    void updateStatus(@Param("statusId") int statusId, @Param("statusName") String statusName);

    @Query(value = "CALL DeleteUserStoryStatus(:statusId)", nativeQuery = true)
    void deleteStatus(@Param("statusId") int statusId);
}
