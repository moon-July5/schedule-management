package com.group4.miniproject.config;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.dto.PrincipalDto;
import com.group4.miniproject.encrypt256.Encrypt256;
import com.group4.miniproject.exception.UserNotFoundException;
import com.group4.miniproject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountRepository accountRepository;
  private Encrypt256 encrypt256 = new Encrypt256();

  /**
   * UserDetailsService 의 loadUserByUsername method를 구현하는데 이는 Database에 접근해서 사용자 정보를 가져오는 역할을 한다.
   * @param accountId, email ??
   * @return
   */
  @Override
  public UserDetails loadUserByUsername(String accountId) throws UserNotFoundException {
    System.out.println("name in loadUserByUsername = " + accountId);
    Account account = null; // db에서 유저조회
    try {
      account = accountRepository.findByAccountId(encrypt256.encryptAES256(accountId))
              .orElseThrow(() -> new UserNotFoundException(accountId + " -> 데이터베이스에서 찾을 수 없습니다."));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // role셋팅
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (AccountRole role: account.getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
    }

    return PrincipalDto.builder()
            .id(account.getId())
            .name(account.getName())
            .password(account.getPassword())
            .accountId(account.getAccountId())
            .email(account.getEmail())
            .build();
  }
}