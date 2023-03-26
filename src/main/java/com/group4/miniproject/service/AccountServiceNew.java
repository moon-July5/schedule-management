package com.group4.miniproject.service;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.SuccessLogin;
import com.group4.miniproject.dto.AccountRequestDTO;
import com.group4.miniproject.dto.ResponseDto;
import com.group4.miniproject.jwt.JwtTokenProvider;
import com.group4.miniproject.repository.AccountRepository;
import com.group4.miniproject.repository.SuccessLoginRepository;
import com.group4.miniproject.util.HttpReqRespUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceNew {
  private final AccountRepository accountRepository;
  private final PasswordEncoder bCryptPasswordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final SuccessLoginRepository successLoginRepository;

  @Transactional
  public ResponseDto signUp(AccountRequestDTO accountRequestDTO){
    System.out.println("signUpReq = " + accountRequestDTO.toString());

    if(accountRepository.existsByEmail(accountRequestDTO.getEmail())) {
      return new ResponseDto("fail", "Your Mail already Exist.");
    }
    if(accountRepository.existsByName(accountRequestDTO.getName())) {
      return new ResponseDto("fail", "Your Name already Exist.");
    }
    if(accountRepository.existsByAccountId(accountRequestDTO.getAccountId())) {
      return new ResponseDto("fail", "Your AccountId already Exist.");
    }
    Account newUser = Account.builder().name(accountRequestDTO.getName()).password(accountRequestDTO.getPassword()).email(accountRequestDTO.getEmail()).accountId(accountRequestDTO.getAccountId()).build();
    newUser.hashPassword(bCryptPasswordEncoder);

    Account user = accountRepository.save(newUser);
    if( user != null ) {
      return new ResponseDto("success", null);
    }
    return new ResponseDto("fail", "Fail to Sign Up");
  }

  /**
   * 유저 정보로 로그인
   * @param accountRequestDTO 유저의 이메일과 비밀번호
   * @return json web token
   */
  public ResponseEntity<ResponseDto> signIn(AccountRequestDTO accountRequestDTO) {
    try {
//      Authentication authentication = authenticationManager.authenticate(
//          new UsernamePasswordAuthenticationToken(
//                accountRequestDTO.getEmail(),
//                accountRequestDTO.getPassword()
//          )
//      );
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      accountRequestDTO.getName(),
                      accountRequestDTO.getPassword()
              )
      );

      // 로그인성공 시 마지막 로그 db에 이력 저장(account_id가 있으면 업데이트)
      Account account = accountRepository.findByName(accountRequestDTO.getName()).get();
      Optional<SuccessLogin> successLoginOptional = successLoginRepository.findByAccount(account);
      SuccessLogin successLogin = null;
      if( !successLoginOptional.isPresent() ) { // 기존에 이력이 없는 경우, 신규 생성
        successLogin = SuccessLogin.builder().account(account).lastSuccessTime(LocalDateTime.now()).userAgent(HttpReqRespUtils.getUserAgent()).clientIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist()).build();
      } else { // 기존에 이력이 있는 경우, update처리
        successLogin = successLoginOptional.get();
        successLogin.setLastSuccessTime(LocalDateTime.now());
        successLogin.setUserAgent(HttpReqRespUtils.getUserAgent());
        successLogin.setClientIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
      }
      successLoginRepository.save(successLogin);

      System.out.println("accountRequestDTO.getName()" + accountRequestDTO.getName());
      String token = jwtTokenProvider.generateAccessToken(authentication); // 토큰생성
      return new ResponseEntity<>(new ResponseDto("success", token), HttpStatus.OK);

    } catch (AuthenticationException e) {
      return new ResponseEntity<>(new ResponseDto("fail", "Invalid credentials supplied"), HttpStatus.OK);
    }
  }

}
