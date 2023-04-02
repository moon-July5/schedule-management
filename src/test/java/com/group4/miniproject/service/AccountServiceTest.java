package com.group4.miniproject.service;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.dto.*;
import com.group4.miniproject.dto.account.AccountDeleteDTO;
import com.group4.miniproject.dto.account.AccountModifyRequestDTO;
import com.group4.miniproject.dto.account.AccountRequestDTO;
import com.group4.miniproject.util.Encrypt256;
import com.group4.miniproject.exception.UserNotFoundException;
import com.group4.miniproject.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Encrypt256 encrypt256 = new Encrypt256();

    @Test
    public void register() throws Exception {
        String name1 = accountService.register("김고수","asdf@1234","마케팅","부장");
        String name2 = accountService.register("이미나","aab@1234","마케팅","과장");
        String name3 = accountService.register("박은우","dkfk@asdf.com","디자인","대리");
        String name4= accountService.register("유지현","yuu@axd.com","회계","사원");
        String name5= accountService.register("민시후","mmm@dfgf.com","인사","사원");
    }

    @Test
    public void signUp() throws Exception {
        AccountRequestDTO ifFalseTest = AccountRequestDTO.builder()
                .accountId("ab1cd")
                .password("123345")
                .email("mmmㅁㄴgf.com")
                .name("유지현")
                .build();
        //4번 유저의 이메일을 다르게 보냈을때 아이디 비밀번호가 넣어지지 않은 것을 확인
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .accountId("abcd")
                .password("12345")
                .email("mmm@dfgf.com")
                .name("민시후")
                .build();
        //5번 유저의 이메일과 이름을 같게 보냈을때 아이디 비밀번호가 넣어지는 것을 확인
        //accountService.signUp(accountRequestDTO);
    }

    @Test
    public void setPasswordEncoderTest() {
        String enPw = passwordEncoder.encode("12345678");
        System.out.println("enPw = " + enPw);
    }

    @Test
    public void findByAccountIdTest1() throws Exception {
        Account account = accountRepository.findByAccountId("admin")
                .orElseThrow(() -> new UserNotFoundException("데이터베이스에서 찾을 수 없습니다."));

        System.out.println("account = " + account.toString());
    }

    @Test
    public void modifyTest() throws Exception {
        AccountModifyRequestDTO accountModifyRequestDTO = AccountModifyRequestDTO.builder()
                .accountId("accountId")
                .newPassword("132456789")
                .password("132456789")
                .email("yuu@axd.com")
                .build();
        try{
            ResponseDto result = accountService.modify(accountModifyRequestDTO);
            log.info(result);
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

    @Test
    public void deleteTest() throws Exception {
        AccountDeleteDTO accountDeleteDTO = AccountDeleteDTO.builder().accountId("accountId").build();
        ResponseDto result = accountService.delete(accountDeleteDTO);
        log.info(result);
    }

}
