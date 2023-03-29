package com.group4.miniproject.dto;

import com.group4.miniproject.domain.AccountRole;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@ToString
@Getter
@Builder
public class AccountLoginResponseDTO {
    String name;

    String accountId;

    Set<AccountRole> accountRole;

    String email;

    String department;

    String position;

    Long yearly;

    boolean duty;

    List<Long> scheduleId ;

    String JWTToken;

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