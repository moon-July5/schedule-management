package com.group4.miniproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class AccountResponseDTO {

    private String name;

    private String email;

    private String department;
}
