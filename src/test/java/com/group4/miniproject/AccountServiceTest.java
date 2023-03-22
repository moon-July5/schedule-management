package com.group4.miniproject;

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
        String name = accountService.register("작동되나","asdf@1234","인사팀","대리");
        log.info(name);
    }
}
