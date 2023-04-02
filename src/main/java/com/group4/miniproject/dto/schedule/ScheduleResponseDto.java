package com.group4.miniproject.dto.schedule;


import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String accountId;
    private String name;
    private String department;
    private String position;
    private List<ScheduleResponseDto.ScheduleData> schedules;

    @Getter
    @Setter
    public static class ScheduleData {
        private Long id;
        private ScheduleType type;
        private String content;
        private String accountId;
        private LocalDateTime start_date;
        private LocalDateTime end_date;
        private LocalDateTime created_at;
        private LocalDateTime modified_at;

        public ScheduleData(Schedule s) {
            this.id = s.getId();
            this.type = s.getType();
            this.content = s.getContent();
            this.accountId = s.getAccount().getAccountId();
            this.start_date = s.getStartDate();
            this.end_date = s.getEndDate();
            this.created_at = s.getCreatedAt();
            this.modified_at = s.getModifiedAt();
        }
    }

    public static ScheduleResponseDto from( Account account) throws Exception {
        return ScheduleResponseDto.builder()
                .id(account.getId())
                .accountId(account.getAccountId())
                .name(account.getName())
                .department(account.getDepartment())
                .position(account.getPosition())
                .schedules(account.getSchedules().stream().map(ScheduleData::new).collect(Collectors.toList()))
                .build();
    }
}
