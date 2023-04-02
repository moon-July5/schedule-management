package com.group4.miniproject.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTodayRequestDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime start_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime end_date;
}
//start_date:현재 날짜, end_date:현재 날짜}