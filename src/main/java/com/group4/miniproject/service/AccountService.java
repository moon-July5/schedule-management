package com.group4.miniproject.service;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.dto.AccountRequestDTO;
import com.group4.miniproject.dto.AccountResponseDTO;

import com.group4.miniproject.encrypt256.Encrypt256;
import com.group4.miniproject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                .position(encrypt256.encryptAES256(position))
                .build();
        accountRepository.save(account);
        return encrypt256.decryptAES256(account.getName());
    }

    public void signUp(AccountRequestDTO accountRequestDTO) throws Exception {
        log.info("-----------------------signUpStart------------------------------");
        String email = encrypt256.encryptAES256(accountRequestDTO.getEmail());
        String name = encrypt256.encryptAES256(accountRequestDTO.getName());
        String accountId = encrypt256.encryptAES256(accountRequestDTO.getAccountId());
        String password = passwordEncoder.encode(accountRequestDTO.getPassword());
//        List<Account> account = accountRepository.findByName(name);
//        for (Account i :account ) {
//            if(i.getEmail().equals(email)){
//                i.setAccountId(accountId);
//                i.setPassword(password);
//                log.info("-----------------------signUp ------------------------------");
//                log.info(i.toString());
//            }
//        }
        log.info("-----------------------signUpFinish------------------------------");
        //예외처리 어디서 할지 고민 아이디가 이미 있는 경우 저장x 추가 해야됨
        //사용중인 아이디 일경우 예외처리
    }
    public void logAccountAll() {
            List<Account> accountList = accountRepository.findAll();
        for (Account i:accountList) {
            log.info(i.toString());
        }
            //테스트에 사용하기 위해서 만듬
        }
}
