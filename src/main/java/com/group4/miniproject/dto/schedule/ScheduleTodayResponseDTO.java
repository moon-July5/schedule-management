package com.group4.miniproject.dto.schedule;

import com.group4.miniproject.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTodayResponseDTO {

        private String name;
        private String department;
        private String position;
        private boolean duty;

}
