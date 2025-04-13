package com.Aptech.userservice.Entitys;

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
@Table(name = "User")
public class User {
    @Id
    @Column(name = "UserId")
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;
    @Column(name = "UserName")
    String userName;
    @Column(name = "Email")
    String email;
    @Column(name = "Password")
    String password;
}
