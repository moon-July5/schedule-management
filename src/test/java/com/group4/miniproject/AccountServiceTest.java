package com.group4.miniproject;

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
        AccountRequestDTO isSuccessTest = AccountRequestDTO.builder()
                .accountId("abcd")
                .password("12345")
                .email("mmm@dfgf.com")
                .name("민시후")
                .build();
        //5번 유저의 이메일과 이름을 같게 보냈을때 아이디 비밀번호가 넣어지는 것을 확인
        try {
            log.info(accountService.signUp(ifFalseTest));
        }catch (Exception e){
        }
        try {
            log.info(accountService.signUp(isSuccessTest));
        }catch (Exception e){
        }
    }

}
