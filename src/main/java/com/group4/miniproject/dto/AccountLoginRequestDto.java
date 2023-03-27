package com.group4.miniproject.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginRequestDto {

    @Size(min=2, max=10 , message="최소 2자에서 최대 10자로 입력해주세요")
    private String accountId;

    @Size(min=8, max=15 , message="비밀번호는 최소 8자에서 최대 15자로 입력해주세요")
    private String password;
}
