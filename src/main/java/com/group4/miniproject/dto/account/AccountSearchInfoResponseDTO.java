package com.group4.miniproject.dto.account;

import com.group4.miniproject.domain.Account;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountSearchInfoResponseDTO {
    private Long id;
    private String accountId;
    private String name;
    private String department;
    private String position;

    public static AccountSearchInfoResponseDTO from(Account account){
        return AccountSearchInfoResponseDTO.builder()
                .id(account.getId())
                .accountId(account.getAccountId())
                .name(account.getName())
                .department(account.getDepartment())
                .position(account.getPosition())
                .build();
    }
}
