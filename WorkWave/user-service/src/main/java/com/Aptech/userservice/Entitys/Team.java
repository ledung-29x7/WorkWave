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
@Table(name = "Team")
public class Team {
    @Id
    @Column(name = "TeamId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer teamId;
    @Column(name = "ProjectId")
    String projectId;
    @Column(name = "Name")
    String teamName;
}
