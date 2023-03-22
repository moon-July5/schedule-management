package com.group4.miniproject.dto;

import com.group4.miniproject.domain.AccountRole;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
@ToString
@Getter
@Builder
public class AccountRequestDTO {

    private String name;

    private String password;

    private String accountId;

    private String email;
    @Builder.Default
    private Set<AccountRole> roles = new HashSet<>();
}
