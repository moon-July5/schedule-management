package com.group4.miniproject.dto;

import com.group4.miniproject.domain.AccountRole;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {
    @NotBlank(message = "이름을 입력해 주세요")
    private String name;

    @Size(min=8, max=15 , message="비밀번호는 최소 8자에서 최대 15자로 입력해주세요")
    private String password;

    @Size(min=2, max=10 , message="최소 2자에서 최대 10자로 입력해주세요")
    private String accountId;

    @Email(message = "email 형식에 맞게 입력해 주세요")
    private String email;

}
