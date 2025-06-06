package com.Aptech.userservice.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UserGlobalRole")
@IdClass(UserGlobalRoleId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGlobalRole {
    @Id
    @Column(name = "UserId", length = 36, nullable = false)
    private String userId;

    @Id
    @Column(name = "RoleId", nullable = false)
    private Integer roleId;

    @Column(name = "AssignedAt")
    private LocalDateTime assignedAt;
}
