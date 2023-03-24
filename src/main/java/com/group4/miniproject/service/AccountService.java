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

import java.util.List;
import java.util.Optional;

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

    public AccountResponseDTO SignUp(Long id, AccountRequestDTO accountRequestDTO) throws Exception {
        log.info("-----------------------signUpStart------------------------------");
        String accountId = encrypt256.encryptAES256(accountRequestDTO.getAccountId());
        String password = passwordEncoder.encode(accountRequestDTO.getPassword());
        Optional<Account> account= accountRepository.findById(id);
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

    public void IdUniqueCheck(String accountId) throws Exception {
        log.info("-----------------------Id Unique Check---------------------------");
        List<Account> accountList =accountRepository.findByAccountId(encrypt256.encryptAES256(accountId));
        if(!accountList.isEmpty()){
            log.info("이미 있는 ID 입니다");
            throw new IllegalArgumentException("이미 있는 ID 입니다");
        }
        log.info("---------------------------Success---------------------------");
        log.info("-----------------------Id Unique Check Finish---------------------------");
    }
    public void NameCheck(String name) throws Exception {
        log.info("-----------------------Name Check ---------------------------------");
        List<Account> accountList = accountRepository.findByName(encrypt256.encryptAES256(name));
        if(accountList.isEmpty()){
            log.info("일치하는 이름이 없습니다.");
            throw new IllegalArgumentException("일치하는 이름이 없습니다.");
        }
        log.info("---------------------------Success---------------------------");
        log.info("-----------------------Name Check Finish---------------------------");
    }
    public Long EmailCheck(String name, String email) throws Exception {
        log.info("-----------------------Email Check ---------------------------------");
        List<Account> accountList = accountRepository.findByName(encrypt256.encryptAES256(name));
        for (Account i :accountList ) {
            if(i.getEmail().equals(encrypt256.encryptAES256(email))){
                log.info("---------------------------Success---------------------------");
                log.info("-----------------------Email Check Finish ---------------------------------");
                return i.getId();
            }
        }
        log.info("일치하는 이메일이 없습니다.");
        log.info("-----------------------Email Check Finish ---------------------------------");
        throw new IllegalArgumentException("일치하는 이메일이 없습니다.");
    }
    public void AlreadySignUpCheck(Long id) throws Exception {
        Optional<Account> account= accountRepository.findById(id);
        log.info("-----------------------Check Already SignUp------------------------------");
        if(account.get().getAccountId()!=null){
            log.info("가입된 아이디가 있습니다.");
            throw new IllegalArgumentException("가입된 아이디가 있습니다.");
        }
        log.info("---------------------------Success---------------------------");
        log.info("-----------------------Check Already SignUp Finish------------------------------");
    }
}
