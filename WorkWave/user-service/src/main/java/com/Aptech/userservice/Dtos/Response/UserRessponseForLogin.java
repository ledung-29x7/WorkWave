package com.Aptech.userservice.Dtos.Response;

import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRessponseForLogin {
    String userId;
    String userName;
    String password;
    String email;
    Set<RoleResponse> roles;
}
