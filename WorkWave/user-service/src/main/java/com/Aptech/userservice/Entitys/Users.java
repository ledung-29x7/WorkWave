package com.Aptech.userservice.Entitys;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Users")
public class Users {
    @Id
    @Column(name = "UserId")
    String userId;
    @Column(name = "UserName")
    String userName;
    @Column(name = "Email")
    String email;
    @Column(name = "Password")
    String password;
    @Column(name = "IsActive")
    Boolean isActive;
    @Column(name = "CreatedAt")
    Date createdAt;
    @Column(name = "UpdatedAt")
    Date updatedAt;
}
