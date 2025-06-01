package com.Aptech.projectservice.Dtos.Request;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SprintRequestDto {
    String name;
    Date startDate;
    Date endDate;
    Integer statusId;
    String goal;
}
