package com.group4.miniproject.service;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.SuccessLogin;
import com.group4.miniproject.dto.*;
import com.group4.miniproject.jwt.JwtTokenProvider;
import com.group4.miniproject.repository.AccountRepository;
import com.group4.miniproject.repository.SuccessLoginRepository;
import com.group4.miniproject.util.HttpReqRespUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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


  @Transactional
  public AccountResponseDTO signUp(AccountRequestDTO accountRequestDTO) throws Exception {
    log.info("-----------------------signUpStart------------------------------");
    if(!accountRepository.existsByEmail(accountRequestDTO.getEmail())) {
      throw new IllegalArgumentException("checkEmail");
    }
    if(!accountRepository.existsByName(accountRequestDTO.getName())) {
      throw new IllegalArgumentException("checkName");
    }
    if(accountRepository.existsByAccountId(accountRequestDTO.getAccountId())) {
      throw new IllegalArgumentException("duplicateId");
    }

    // pk 추출
    Long id = accountRepository.findByEmail(accountRequestDTO.getEmail()).get().getId();

    // 사용자 조회
    Optional<Account> account= accountRepository.findById(id);

    // 이미 존재하고 있는 아이디인지 확인
    if(account.get().getAccountId()!=null){
      throw new IllegalArgumentException("existId");
    }

    String accountId =accountRequestDTO.getAccountId();
    String password = passwordEncoder.encode(accountRequestDTO.getPassword());


    account.get().setAccountId(accountId);
    account.get().setPassword(password);
    AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
            .department(account.get().getDepartment())
            .email(account.get().getEmail())
            .name(account.get().getName())
            .build();
    log.info("-----------------------signUpFinish------------------------------");
    return accountResponseDTO;
  }


  /**
   * 유저 정보로 로그인
   * @param accountRequestDTO 유저의 이메일과 비밀번호
   * @return json web token
   */
  public ResponseEntity<AccountLoginResponseDTO> signIn(AccountLoginRequestDto accountRequestDTO) {
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
      Account account = accountRepository.findByAccountId(accountRequestDTO.getAccountId())
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
//      List<Long> scheduleId = new ArrayList<>();
//      for (Schedule i:account.getSchedules()) {
//            scheduleId.add(i.getId());
//      }

      System.out.println("accountRequestDTO.getAccountId()" + accountRequestDTO.getAccountId());
      String token = jwtTokenProvider.generateAccessToken(authentication); // 토큰생성
//      AccountLoginResponseDTO accountLoginResponseDTO= AccountLoginResponseDTO.builder()
//              .accountRole(account.getRoles())
//              .accountId(account.getAccountId())
//              .yearly(account.getYearly())
//              .duty(account.getDuty())
//              .department(account.getDepartment())
//              .position(account.getPosition())
//              .email(account.getEmail())
//              .name(account.getName())
//              .scheduleId(scheduleId)
//              .JWTToken(token)
//              .build();
      Schedule schedule = nearSchedule(account.getSchedules());
      return new ResponseEntity<>(AccountLoginResponseDTO.from(account,schedule,token), HttpStatus.OK);

    } catch (AuthenticationException e) {
      throw new BadCredentialsException("로그인에 실패하셨습니다");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  private Schedule nearSchedule(List<Schedule> scheduleList){
    LocalDateTime now = LocalDateTime.now();
    int count = -1 , count2=Integer.MAX_VALUE;
    for (Schedule i:scheduleList) {
        if(count2 <(int) ChronoUnit.DAYS.between(i.getStartDate(),now)){
        break;
        }
        count2=(int) ChronoUnit.DAYS.between(i.getStartDate(),now);
        count++;
    }
    return scheduleList.get(count);
  }

  public ResponseDto modify(AccountModifyRequestDTO accountModifyRequestDTO) throws Exception {
    String accountId = accountModifyRequestDTO.getAccountId();
    String email = accountModifyRequestDTO.getEmail();
    String newPassword= passwordEncoder.encode(accountModifyRequestDTO.getNewPassword());

    Optional<Account> account = accountRepository.findByAccountId(accountId);
//    passwordEncoder.matches()   사용해보자...
    try{
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      accountModifyRequestDTO.getAccountId(),
                      accountModifyRequestDTO.getPassword()
              )
      );
      account.get().setPassword(newPassword);
      account.get().setEmail(email);
      log.info(account.get());
      accountRepository.save(account.get());
      return new ResponseDto("success","수정완료");
    }catch (AuthenticationException e) {
      throw new IllegalArgumentException("비밀번호를 확인해 주세요");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }
  public ResponseDto delete(AccountDeleteDTO accountDeleteDTO) throws Exception {
    if(!accountRepository.existsByAccountId(accountDeleteDTO.getAccountId())) {
      throw new IllegalArgumentException("존재하지 않는 아이디 입니다.");
    }
    String accountId = accountDeleteDTO.getAccountId();
    Optional<Account> account = accountRepository.findByAccountId(accountId);


    accountRepository.deleteById(account.get().getId());
    return new ResponseDto("success","삭제완료");
    //  로그인 기능 쪽에  isdelete true면 로그인 불가 ?.
  }
  public AccountSearchResponseDTO search(AccountSearchRequestDTO accountSearchRequestDTO) throws Exception {
    Optional<Account> account = accountRepository.findByName(accountSearchRequestDTO.getName());
    return AccountSearchResponseDTO.from(account.get());
  }

  // 테스트 용
  public String register(String name, String email,String department, String position) throws Exception {
    Account account = Account.builder()
            .name(name)
            .email(email)
            .department(department)
            .position(position)
            .build();

    accountRepository.save(account);
    return account.getName();
  }

}
