package com.group4.miniproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTodayRequestDTO {
    private LocalDateTime start_date;

    private LocalDateTime end_date;
}
//start_date:현재 날짜, end_date:현재 날짜}