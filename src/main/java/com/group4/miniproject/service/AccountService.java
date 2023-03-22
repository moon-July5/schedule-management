package com.group4.miniproject.service;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.encrypt256.Encrypt256;
import com.group4.miniproject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private Encrypt256 encrypt256 = new Encrypt256();

    public String register(String name, String email,String department, String position) throws Exception {
        Account account = Account.builder()
                .name(encrypt256.encryptAES256(name))
                .email(encrypt256.encryptAES256(email))
                .department(encrypt256.encryptAES256(department))
                .position((position))
                .build();
        accountRepository.save(account);
        return encrypt256.decryptAES256(account.getName());
    }


//    public AccountResponseDto signUp(AccountRequestDTO accountRequestDTO){
//
//
//        return
//    }
//
//    public Long modify(){
//
//        return
//    }

}
