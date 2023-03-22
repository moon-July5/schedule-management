package com.group4.miniproject.dto;

import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.SuccessLogin;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
@ToString
@Getter
@Builder
public class AccountResponseDTO {


    private String name;

    private String accountId;


    @Builder.Default
    private Set<AccountRole> roles = new HashSet<>();


    private Set<Schedule> schedules = new LinkedHashSet<>();

    @ToString.Exclude
    private SuccessLogin successLogin;

    private String email;

    private String department;

    private String position;

    private Long yearly;

    @Builder.Default
    private Boolean duty = Boolean.FALSE;

    private LocalDateTime deletedAt;

    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;
}
