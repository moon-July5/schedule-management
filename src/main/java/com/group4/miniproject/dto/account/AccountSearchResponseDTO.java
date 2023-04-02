package com.group4.miniproject.dto.account;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
import com.group4.miniproject.dto.schedule.ScheduleResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@Builder
public class AccountSearchResponseDTO {
    private Long id;
    private String accountId;
    private String name;
    private String department;
    private String position;
    private AccountRole role;
    private String email;
    private Long yearly;
    private boolean duty;

    private List<ScheduleResponseDto.ScheduleData> schedules;

    @Getter
    @Setter
    public static class ScheduleData {
        private Long id;
        private ScheduleType type;
        private String content;
        private LocalDateTime start_date;
        private LocalDateTime end_date;
        private LocalDateTime created_at;
        private LocalDateTime modified_at;

        public ScheduleData(Schedule s) {
            this.id =s.getId();
            this.type = s.getType();
            this.content = s.getContent();
            this.start_date = s.getStartDate();
            this.end_date = s.getEndDate();
            this.created_at = s.getCreatedAt();
            this.modified_at = s.getModifiedAt();
        }
    }

    public static AccountSearchResponseDTO from(Account account) throws Exception {
        return AccountSearchResponseDTO.builder()
                .id(account.getId())
                .accountId(account.getAccountId())
                .name(account.getName())
                .department(account.getDepartment())
                .position(account.getPosition())
                .role(account.getRoles().iterator().next())
                .email(account.getEmail())
                .yearly(account.getYearly())
                .duty(account.getDuty())
                .schedules(account.getSchedules().stream().map(ScheduleResponseDto.ScheduleData::new).collect(Collectors.toList()))
                .build();
    }
}
