package com.group4.miniproject.service;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.SuccessLogin;
import com.group4.miniproject.dto.AccountLoginRequestDto;
import com.group4.miniproject.dto.AccountRequestDTO;
import com.group4.miniproject.dto.AccountResponseDTO;
import com.group4.miniproject.dto.ResponseDto;
import com.group4.miniproject.encrypt256.Encrypt256;
import com.group4.miniproject.jwt.JwtTokenProvider;
import com.group4.miniproject.repository.AccountRepository;
import com.group4.miniproject.repository.SuccessLoginRepository;
import com.group4.miniproject.util.HttpReqRespUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final SuccessLoginRepository successLoginRepository;
  private Encrypt256 encrypt256 = new Encrypt256();

  @Transactional
  public AccountResponseDTO signUp(AccountRequestDTO accountRequestDTO) throws Exception {
    log.info("-----------------------signUpStart------------------------------");
    if(!accountRepository.existsByEmail(encrypt256.encryptAES256(accountRequestDTO.getEmail()))) {
      throw new IllegalArgumentException("일치하는 이메일이 없습니다.");
    }
    if(!accountRepository.existsByName(encrypt256.encryptAES256(accountRequestDTO.getName()))) {
      throw new IllegalArgumentException("일치하는 이름이 없습니다.");
    }
    if(accountRepository.existsByAccountId(encrypt256.encryptAES256(accountRequestDTO.getAccountId()))) {
      throw new IllegalArgumentException("이미 존재하고 있는 아이디 입니다");
    }

    // pk 추출
    Long id = accountRepository.findByEmail(encrypt256.encryptAES256(accountRequestDTO.getEmail())).get().getId();

    // 사용자 조회
    Optional<Account> account= accountRepository.findById(id);

    // 이미 존재하고 있는 아이디인지 확인
    if(account.get().getAccountId()!=null){
      throw new IllegalArgumentException("가입된 아이디가 있습니다.");
    }

    String accountId = encrypt256.encryptAES256(accountRequestDTO.getAccountId());
    String password = passwordEncoder.encode(accountRequestDTO.getPassword());


    account.get().setAccountId(accountId);
    account.get().setPassword(password);
    AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
            .department(encrypt256.decryptAES256(account.get().getDepartment()))
            .email(encrypt256.decryptAES256(account.get().getEmail()))
            .name(encrypt256.decryptAES256(account.get().getName()))
            .build();
    log.info("-----------------------signUpFinish------------------------------");
    return accountResponseDTO;
  }


  /**
   * 유저 정보로 로그인
   * @param accountRequestDTO 유저의 이메일과 비밀번호
   * @return json web token
   */
  public ResponseEntity<ResponseDto> signIn(AccountLoginRequestDto accountRequestDTO) {
    try {
//      Authentication authentication = authenticationManager.authenticate(
//          new UsernamePasswordAuthenticationToken(
//                accountRequestDTO.getEmail(),
//                accountRequestDTO.getPassword()
//          )
//      );
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      accountRequestDTO.getAccountId(),
                      accountRequestDTO.getPassword()
              )
      );

      // 로그인성공 시 마지막 로그 db에 이력 저장(account_id가 있으면 업데이트)
      Account account = accountRepository.findByAccountId(encrypt256.encryptAES256(accountRequestDTO.getAccountId()))
              .get();

      Optional<SuccessLogin> successLoginOptional = successLoginRepository.findByAccount(account);
      SuccessLogin successLogin = null;
      if( !successLoginOptional.isPresent() ) { // 기존에 이력이 없는 경우, 신규 생성
        successLogin = SuccessLogin.builder().account(account).lastSuccessTime(LocalDateTime.now())
                .userAgent(HttpReqRespUtils.getUserAgent())
                .clientIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist())
                .build();

      } else { // 기존에 이력이 있는 경우, update처리
        successLogin = successLoginOptional.get();
        successLogin.setLastSuccessTime(LocalDateTime.now());
        successLogin.setUserAgent(HttpReqRespUtils.getUserAgent());
        successLogin.setClientIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
      }
      successLoginRepository.save(successLogin);

      System.out.println("accountRequestDTO.getAccountId()" + accountRequestDTO.getAccountId());
      String token = jwtTokenProvider.generateAccessToken(authentication); // 토큰생성
      return new ResponseEntity<>(new ResponseDto("success", token), HttpStatus.OK);

    } catch (AuthenticationException e) {
      return new ResponseEntity<>(new ResponseDto("fail", "Invalid credentials supplied"), HttpStatus.OK);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // 테스트 용
  public String register(String name, String email,String department, String position) throws Exception {
    Account account = Account.builder()
            .name(encrypt256.encryptAES256(name))
            .email(encrypt256.encryptAES256(email))
            .department(encrypt256.encryptAES256(department))
            .position(encrypt256.encryptAES256(position))
            .build();

    accountRepository.save(account);
    return encrypt256.decryptAES256(account.getName());
  }

}
