package com.group4.miniproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountModifyRequestDTO {

    private String accountId ;
    private String email;
    private String password ;
    private String newPassword;
}
//{id:1, accountId’test2’, password:’’12345, name:’’홍길동’, role:’ROLE_USER’