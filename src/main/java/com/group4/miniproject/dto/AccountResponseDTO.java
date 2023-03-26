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

    private String email;

    private String department;
}
