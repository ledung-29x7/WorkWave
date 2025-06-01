package com.Aptech.userservice.Repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Entitys.Users;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, String>, UserRepositoryCustom {

        @Modifying
        @Transactional
        @Query(value = "CALL sp_update_user(:userId, :userName, :email)", nativeQuery = true)
        void updateUserByProcedure(@Param("userId") String userId,
                        @Param("userName") String userName,
                        @Param("email") String email);

        @Modifying
        @Transactional
        @Query(value = "CALL sp_delete_user(:userId)", nativeQuery = true)
        void deleteUserByProcedure(@Param("userId") String userId);

        @Query(value = "CALL sp_search_users(:keyword)", nativeQuery = true)
        List<Users> searchUsersByProcedure(@Param("keyword") String keyword);

        @Query(value = "CALL sp_search_users_with_paging(:keyword, :pageNumber, :pageSize)", nativeQuery = true)
        List<Object[]> searchUsersPaged(
                        @Param("keyword") String keyword,
                        @Param("pageNumber") int pageNumber,
                        @Param("pageSize") int pageSize);

        @Query(value = "CALL existsByEmail(:email)", nativeQuery = true)
        Integer ExistsByEmail(@Param("email") String email);

        @Query(value = "CALL findByEmail(:email)", nativeQuery = true)
        Optional<Users> FindByEmail(@Param("email") String email);

}
