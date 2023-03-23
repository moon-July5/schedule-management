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


    public AccountResponseDTO signUp(AccountRequestDTO accountRequestDTO) throws Exception {
        log.info("-----------------------signUpStart------------------------------");
        String email = encrypt256.encryptAES256(accountRequestDTO.getEmail());
        String name = encrypt256.encryptAES256(accountRequestDTO.getName());
        String accountId = encrypt256.encryptAES256(accountRequestDTO.getAccountId());
        String password = passwordEncoder.encode(accountRequestDTO.getPassword());
        log.info("-----------------------Id Unique Check---------------------------");
        if(accountRepository.findByAccountId(accountId)!=null){
            log.info("이미 있는 ID 입니다");
            throw new IllegalStateException("이미 있는 ID 입니다");
        }
        log.info("-----------------------Id Unique Check Finish---------------------------");

        List<Account> account = accountRepository.findByName(name);
        for (Account i :account ) {
            if(i.getEmail().equals(email)){
                if(i.getAccountId()!= null){
                    log.info("가입된 아이디가 있습니다.");
                    throw new IllegalStateException("가입된 아이디가 있습니다.");
                }
                i.setAccountId(accountId);
                i.setPassword(password);
                log.info("-----------------------signUp ------------------------------");
                log.info(i.toString());

                AccountResponseDTO accountResponseDTO = AccountResponseDTO.builder()
                        .department(encrypt256.decryptAES256(i.getDepartment()))
                        .email(encrypt256.decryptAES256(i.getEmail()))
                        .name(encrypt256.decryptAES256(i.getName()))
                        .build();
                log.info("회원가입 성공");
                return accountResponseDTO;
            }
        }
        log.info("-----------------------signUpFinish------------------------------");
        log.info("이름과 이메일이 정확한지 확인해 주세요");
        throw new IllegalStateException("이름과 이메일이 정확한지 확인해 주세요");
        //예외처리 어디서 할지 고민 아이디가 이미 있는 경우 저장x 추가 해야됨
        //사용중인 아이디 일경우 예외처리
    }

}
