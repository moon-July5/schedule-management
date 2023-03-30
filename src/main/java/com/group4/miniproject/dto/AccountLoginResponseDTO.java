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
    private String name;

    private String accountId;

    private Set<AccountRole> accountRole;

    private String email;

    private String department;

    private String position;

    private Long yearly;

    boolean duty;

    private List<Long> scheduleId ;

    private String JWTToken;

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