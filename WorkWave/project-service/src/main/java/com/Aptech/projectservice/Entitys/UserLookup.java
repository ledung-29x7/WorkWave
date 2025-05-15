package com.Aptech.projectservice.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserLookup {
    @Id
    private String userId;
    private String userName;
    private String email;
    private LocalDateTime createdAt = LocalDateTime.now();
}
