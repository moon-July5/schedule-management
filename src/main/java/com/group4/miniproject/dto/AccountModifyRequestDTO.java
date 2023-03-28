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

    String accountId ;
    String email;
    String password ;
    String newPassword;
}
//{id:1, accountId’test2’, password:’’12345, name:’’홍길동’, role:’ROLE_USER’