package com.Aptech.userservice.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aptech.userservice.Dtos.Response.UserDetailProjection;
import com.Aptech.userservice.Dtos.Response.UserProjection;
import com.Aptech.userservice.Dtos.Response.UserRessponseForLoginProjection;
import com.Aptech.userservice.Entitys.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

        @Query(value = "CALL ExistsByUserName(:username_param)", nativeQuery = true)
        int ExistsByUserName(@Param("username_param") String username_param);

        @Modifying
        @Transactional
        @Query(value = "CALL CreateUser(:userName, :email, :password)", nativeQuery = true)
        void CreateUser(
                        @Param("userName") String userName,
                        @Param("email") String email,
                        @Param("password") String password);

        @Query(value = "CALL GetAllUsers(:pageNumber, :pageSize, :searchName, :searchEmail)", nativeQuery = true)
        List<UserProjection> getAllUsers(
                        @Param("pageNumber") int pageNumber,
                        @Param("pageSize") int pageSize,
                        @Param("searchName") String searchName,
                        @Param("searchEmail") String searchEmail);

        @Modifying
        @Transactional
        @Query(value = "CALL DeleteUser(:userId_param)", nativeQuery = true)
        void DeleteUser(@Param("userId_param") String userId_param);

        @Query(value = "CALL GetUserById(:userId)", nativeQuery = true)
        UserDetailProjection getUserById(@Param("userId") String userId);

        @Query(value = "CALL GetUserByUserName(:userName)", nativeQuery = true)
        UserRessponseForLoginProjection getUserByUserName(@Param("userName") String userName);
}
