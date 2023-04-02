package com.group4.miniproject.dto.account;

import com.group4.miniproject.domain.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountRoleRequestDTO {
    private AccountRole role;
}
