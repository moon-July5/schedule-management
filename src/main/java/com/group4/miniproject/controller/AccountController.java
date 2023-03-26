package com.group4.miniproject.controller;

import com.group4.miniproject.dto.AccountRequestDTO;
import com.group4.miniproject.dto.ResponseDto;
import com.group4.miniproject.service.AccountService;
import com.group4.miniproject.service.AccountServiceNew;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class AccountController {
    private final AccountService accountService;

    private final AccountServiceNew accountServiceNew;


//    @PostMapping("/signup")
//    public String signup(){
//
//        return ""
//    }

    @PostMapping("/signup")
    public ResponseDto signUp(@Validated AccountRequestDTO accountRequestDTO) {
        return accountServiceNew.signUp(accountRequestDTO);
    }

    //    @PostMapping("/signin")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> signIn(@Validated AccountRequestDTO accountRequestDTO) {
        return accountServiceNew.signIn(accountRequestDTO);
    }
}
