package com.Aptech.testservice.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "UserLookup")
public class UserLookup {
    @Id
    private String userId;
    private String userName;
    private String email;
    private LocalDateTime createdAt = LocalDateTime.now();
}
