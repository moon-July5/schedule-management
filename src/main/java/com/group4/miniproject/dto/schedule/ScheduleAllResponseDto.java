package com.group4.miniproject.dto.schedule;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
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
     public List<ScheduleResponseDto> users = new ArrayList<>();


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
