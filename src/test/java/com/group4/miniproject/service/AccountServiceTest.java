package com.group4.miniproject.service;

import com.group4.miniproject.dto.AccountRequestDTO;
import com.group4.miniproject.service.AccountService;
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
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void registerTest() throws Exception {
        String name1 = accountService.register("김고수","asdf@1234","마케팅","부장");
        String name2 = accountService.register("이미나","aab@1234","마케팅","과장");
        String name3 = accountService.register("박은우","dkfk@asdf.com","디자인","대리");
        String name4= accountService.register("유지현","yuu@axd.com","회계","사원");
        String name5= accountService.register("민시후","mmm@dfgf.com","인사","사원");
    }
    @Test
    public void signUpTest() throws Exception {
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .accountId("abcd")
                .password("12345")
                .build();
        log.info(accountService.SignUp(5l,accountRequestDTO));

    }
    @Test
    public void IdUniqueCheckTest() throws Exception {
        //중복안된아이디
        accountService.IdUniqueCheck("abbb");
        //중복아이디
        try {
            accountService.IdUniqueCheck("user2");
        }catch (Exception e){

        }

    }
    @Test
    public void NameCheckTest() throws Exception {
        //등록된 이름
        accountService.NameCheck("김고수");

        try {
            //등록되지 않은 이름
            accountService.NameCheck("등록되지 않은 이름");
        }catch (Exception e){

        }
    }
    @Test
    public void EmailCheckTest() throws Exception {
        //등록된 이름, 이메일
        log.info(accountService.EmailCheck("박은우", "dkfk@asdf.com"));
        try {
            //등록되지 않은 이름, 이메일
            log.info(accountService.EmailCheck("등록되지 않은 이름","asdfg@hhh"));
        }catch (Exception e){

        }
    }
    @Test
    public void AlreadySignUpCheck() throws Exception {
        //가입을 하지 않은 id
        accountService.AlreadySignUpCheck(4l);
        try {
            //가입을 한 id
            accountService.AlreadySignUpCheck(1l);
        }catch (Exception e){

        }
    }

}
