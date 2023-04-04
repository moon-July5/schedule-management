package com.group4.miniproject.dto.schedule;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
import com.group4.miniproject.dto.PrincipalDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
    private Long id;
    @NotNull(message = "날짜를 선택해 주세요.")
    private LocalDateTime start_date;

    @NotNull(message = "날짜를 선택해 주세요.")
    private LocalDateTime end_date;

    private ScheduleType scheduleType;

    private String content;

    public Schedule toEntity(PrincipalDto dto) {
        return Schedule.builder()
                .startDate(start_date)
                .endDate(end_date)
                .type(scheduleType)
                .content(content)
                .account(dto.toEntity())
                .build();

    }

    public Schedule adminToEntity(Account account) {
        return Schedule.builder()
                .startDate(start_date)
                .endDate(end_date)
                .type(scheduleType)
                .content(content)
                .account(Account.builder().id(id).build())
                .build();
    }
}
