package com.group4.miniproject.dto.schedule;

import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleAllResponseDto {
    private Long id;
    private String accountId;
    private LocalDateTime start_date, end_date;
    private LocalDateTime created_at, modified_at;
    private ScheduleType type; // 연차, 당직
    private String content; // 일정 내용

    public ScheduleAllResponseDto(Schedule s) {
        this.id = s.getId();
        this.accountId = s.getAccount().getAccountId();
        this.type = s.getType();
        this.content = s.getContent();
        this.start_date = s.getStartDate();
        this.end_date = s.getEndDate();
        this.created_at = s.getCreatedAt();
        this.modified_at = s.getModifiedAt();
    }

}
