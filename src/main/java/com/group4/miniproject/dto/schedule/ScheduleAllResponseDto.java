package com.group4.miniproject.dto.schedule;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
import com.group4.miniproject.dto.account.AccountLoginRequestDto;
import com.group4.miniproject.dto.account.AccountLoginResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleAllResponseDto {
     public List<UserData> users = new ArrayList<>();

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

          private AccountLoginResponseDTO.ScheduleData schedule;
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
               this.schedule = new AccountLoginResponseDTO.ScheduleData(s);
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

}
/*
users: [
          {
            name: ‘홍길동’,
            accountId: ‘abc123’,
            role: ‘ROLE_USER’,
            email: ‘gildong123@naver.com’,
            department: ‘개발팀‘,
            position: ‘팀장’,
            yearly: ‘30’,
            duty: false,
            schedules: {
              id: ‘1’,
              accountId: ‘abc123’,
              type: ‘YEARLY’,
              content: null,
              start_date: ‘2023-03-23T00:00:00Z’,
              end_date: ‘2023-03-24T00:00:00Z’,
              created_at: ‘2023-03-15T15:25:00Z’,
              modified_at: ‘2023-03-15T15:25:00Z’,
            },
          },
        ]
 */
