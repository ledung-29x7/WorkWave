package com.Aptech.bugtrackingservice.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.*;
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
@Table(name = "BugHistory")
public class BugHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BugHistoryId")
    Integer bugHistoryId;

    @Column(name = "BugId")
    Integer bugId;

    @Column(name = "StatusId")
    Integer statusId;

    @Column(name = "UpdatedBy")
    String updatedBy;

    @Column(name = "UpdatedAt")
    LocalDateTime updatedAt;

    @Column(name = "Comment")
    String comment;

}
