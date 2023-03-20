package com.group4.miniproject.domain;


import lombok.Getter;

@Getter
public enum AccountRole {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String role;

    AccountRole(String role){
        this.role = role;
    }
}
