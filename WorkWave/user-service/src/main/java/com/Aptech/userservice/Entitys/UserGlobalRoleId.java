package com.Aptech.userservice.Entitys;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGlobalRoleId implements Serializable {
    private String userId;
    private Integer roleId;

}
