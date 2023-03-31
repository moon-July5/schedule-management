package com.group4.miniproject.dto;

import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleAllResponseDto {
    private Long id;
    private Long account_id;
    private LocalDateTime startDate, endDate;
    private LocalDateTime createdAt, modifiedAt;
    private ScheduleType type; // 연차, 당직
    private String content; // 일정 내용

    public ScheduleAllResponseDto(Schedule s) {
        this.id = s.getId();
        this.account_id = s.getAccount().getId();
        this.type = s.getType();
        this.content = s.getContent();
        this.startDate = s.getStartDate();
        this.endDate = s.getEndDate();
        this.createdAt = s.getCreatedAt();
        this.modifiedAt = s.getModifiedAt();
    }

}
