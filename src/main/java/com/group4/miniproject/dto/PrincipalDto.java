package com.group4.miniproject.dto;

import com.group4.miniproject.domain.Account;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Data
public class PrincipalDto implements UserDetails {
    private Long id;
    private String name;
    private String password;
    private String accountId;
    private String email;

    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.accountId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return  true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Account toEntity(){
        return Account.builder()
                .id(id)
                .name(name)
                .accountId(accountId)
                .password(password)
                .email(email)
                .build();
    }
}
