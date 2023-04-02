package com.group4.miniproject.dto.account;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountSearchInfoResponseDTO {
    private Long id;
    private String accountId;
    private String name;
    private String department;
    private String position;
    private AccountRole role;
    private String email;
    private Long yearly;
    private boolean duty;

    public static AccountSearchInfoResponseDTO from(Account account){
        return AccountSearchInfoResponseDTO.builder()
                .id(account.getId())
                .accountId(account.getAccountId())
                .name(account.getName())
                .department(account.getDepartment())
                .position(account.getPosition())
                .role(account.getRoles().iterator().next())
                .email(account.getEmail())
                .yearly(account.getYearly())
                .duty(account.getDuty())
                .build();
    }
}
