package com.group4.miniproject;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void insertAccount(){
        IntStream.rangeClosed(1,10).forEach(i ->{
            Account account = Account.builder()
                    .accountId("account"+i)
                    .email("email"+i+"@aaa.bbb")
                    .name("이름"+i)
                    .password(passwordEncoder.encode("1111"))
                    .build();
            accountRepository.save(account);
        });
    }
}
