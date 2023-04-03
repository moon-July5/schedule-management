package com.group4.miniproject.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTodayRequestDTO {

    private LocalDate start_date;


    private LocalDate end_date;
}
//start_date:현재 날짜, end_date:현재 날짜}