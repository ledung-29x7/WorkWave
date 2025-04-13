package com.Aptech.userservice.Controllers;

import java.util.List;

import com.Aptech.userservice.Dtos.Request.AssignTeamRequest;
import com.Aptech.userservice.Dtos.Request.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Aptech.userservice.Dtos.Request.UserCreationRequest;
import com.Aptech.userservice.Dtos.Request.UserRoleCreationRequest;
import com.Aptech.userservice.Dtos.Response.ApiResponse;
import com.Aptech.userservice.Dtos.Response.GetAllUserResponse;
import com.Aptech.userservice.Dtos.Response.UserResponse;
import com.Aptech.userservice.Services.Interfaces.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/createuser")
    public ApiResponse<UserResponse> CreateUser(@RequestBody UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.CreateUser(request))
                .build();
    }

    @GetMapping("/getalluser")
    public ApiResponse<GetAllUserResponse> getAllUsers(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) String searchEmail) {
        var result = userService.getAllUsers(pageNumber, pageSize, searchName, searchEmail);
        return ApiResponse.<GetAllUserResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/delete/{userId}")
    ApiResponse<String> DeleteUser(@PathVariable("userId") String userId) {
        userService.DeleteUser(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @PostMapping("/assignrole")
    ApiResponse<String> AssignRole(@RequestBody UserRoleCreationRequest request) {
        userService.AssignRole(request);
        return ApiResponse.<String>builder().result("Assigled ").build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> GetUser(@PathVariable("userId") String userId) {
        UserResponse user = userService.getUserById(userId);
        return ApiResponse.<UserResponse>builder()
                .result(user)
                .build();
    }

    @PostMapping("/assignteam")
    public ApiResponse<String> AssignTeam(@RequestBody AssignTeamRequest request) {
        userService.AssignTeam(request);
        return  ApiResponse.<String>builder().result("Assigled").build();
    }

}
