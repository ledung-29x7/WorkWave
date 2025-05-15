package com.Aptech.userservice.Controllers;

import com.Aptech.userservice.Dtos.Request.AssignTeamRequest;

import java.util.List;

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
import com.Aptech.userservice.Entitys.ProjectLookup;
import com.Aptech.userservice.Services.Interfaces.UserService;

import jakarta.ws.rs.PathParam;
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
        UserResponse response = userService.CreateUser(request);
        return ApiResponse.<UserResponse>builder()
                .status("success")
                .data(response)
                .build();
    }

    @GetMapping("/getalluser")
    public ApiResponse<GetAllUserResponse> getAllUsers(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) String searchEmail) {
        GetAllUserResponse result = userService.getAllUsers(pageNumber, pageSize, searchName, searchEmail);
        return ApiResponse.<GetAllUserResponse>builder()
                .status("success")
                .data(result)
                .build();
    }

    @GetMapping("/project/{userId}")
    public ApiResponse<List<ProjectLookup>> getProjectByUserId(@PathVariable("userId") String userId) {
        List<ProjectLookup> result = userService.GetProjectsByUserId(userId);
        return ApiResponse.<List<ProjectLookup>>builder()
                .status("success")
                .data(result)
                .build();
    }

    @PostMapping("/delete/{userId}")
    public ApiResponse<String> DeleteUser(@PathVariable("userId") String userId) {
        userService.DeleteUser(userId);
        return ApiResponse.<String>builder()
                .status("success")
                .data("User has been deleted")
                .build();
    }

    @PostMapping("/assignrole")
    public ApiResponse<String> AssignRole(@RequestBody UserRoleCreationRequest request) {
        userService.AssignRole(request);
        return ApiResponse.<String>builder()
                .status("success")
                .data("Assigned")
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> GetUser(@PathVariable("userId") String userId) {
        UserResponse user = userService.getUserById(userId);
        return ApiResponse.<UserResponse>builder()
                .status("success")
                .data(user)
                .build();
    }

    @PostMapping("/assignteam")
    public ApiResponse<String> AssignTeam(@RequestBody AssignTeamRequest request) {
        userService.AssignTeam(request);
        return ApiResponse.<String>builder()
                .status("success")
                .data("Assigned")
                .build();
    }

}
