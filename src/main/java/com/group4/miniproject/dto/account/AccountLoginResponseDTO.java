package com.group4.miniproject.dto.account;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginResponseDTO {
    private UserData user;

    private String accessToken;
    @Getter
    @Setter
    public static class UserData {
        private Long id;
        private String name;

        private String accountId;

        private AccountRole role;

        private String email;

        private String department;

        private String position;

        private Long yearly;

        boolean duty;

        private ScheduleData schedule;
        public UserData(Account a,Schedule s) {
            this.id = a.getId();
            this.name = a.getName();
            this.accountId = a.getAccountId();
            this.role = a.getRoles().iterator().next();
            this.email = a.getEmail();
            this.department = a.getDepartment();
            this.position = a.getPosition();
            this.yearly = a.getYearly();
            this.duty = a.getDuty();
            this.schedule = new ScheduleData(s);
        }
    }


    @Getter
    @Setter
    public static class ScheduleData {
        private Long id;
        private String accountId;
        private ScheduleType type;
        private String content;
        private LocalDateTime start_date;
        private LocalDateTime end_date;
        private LocalDateTime created_at;
        private LocalDateTime modified_at;

        public ScheduleData(Schedule s) {
            if(s==null){
                return;
            }
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

    public static AccountLoginResponseDTO from( Account account,Schedule schedule,String accessToken) throws Exception {
        return AccountLoginResponseDTO.builder()
                .user(new UserData(account,schedule))
                .accessToken(accessToken)
                .build();
    }
}
/*
* "name": "Nicolas Serrano Arevalo",
"account_id": "nico123",
"role": "user",
"email": "nico321@gmail.com",
"department": "개발팀",
"position": "팀장",
"yearly": 24,
"duty": true*/