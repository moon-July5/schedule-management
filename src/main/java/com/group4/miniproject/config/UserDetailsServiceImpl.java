package com.group4.miniproject.config;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.exception.UserNotFoundException;
import com.group4.miniproject.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//  @Autowired
//  private UserRepository userRepository;

  @Autowired
  private AccountRepository accountRepository;

  /**
   * UserDetailsService 의 loadUserByUsername method를 구현하는데 이는 Database에 접근해서 사용자 정보를 가져오는 역할을 한다.
   * @param email
   * @return
   */
//  @Override
//  public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
//    System.out.println("email in loadUserByUsername = " + email);
//    UserAccount user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));
//    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//
//    return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
//  }

  /**
   * UserDetailsService 의 loadUserByUsername method를 구현하는데 이는 Database에 접근해서 사용자 정보를 가져오는 역할을 한다.
   * @param name, accountId, email ??
   * @return
   */
  @Override
  public UserDetails loadUserByUsername(String name) throws UserNotFoundException {
//    System.out.println("accountId in loadUserByUsername = " + accountId);
//    Account user = accountRepository.findByAccountId(accountId).orElseThrow(() -> new UserNotFoundException(accountId + " -> 데이터베이스에서 찾을 수 없습니다."));

//    System.out.println("email in loadUserByUsername = " + email);
//    Account user = accountRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));

    System.out.println("name in loadUserByUsername = " + name);
    Account user = accountRepository.findByName(name).orElseThrow(() -> new UserNotFoundException(name + " -> 데이터베이스에서 찾을 수 없습니다.")); // db에서 유저조회
    // role셋팅
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (AccountRole role: user.getRoles()) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
    }
//    user.getRoles().stream()
//            .map(p -> new SimpleGrantedAuthority(p.name()))
//            .forEach(grantedAuthorities::add);

//    System.out.println("---------------------------------------");
//    System.out.println(user);
//    System.out.println("---------------------------------------");

    return new User(user.getName(), user.getPassword(), grantedAuthorities);
  }
}