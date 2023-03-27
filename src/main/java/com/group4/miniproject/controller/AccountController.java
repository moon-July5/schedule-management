package com.group4.miniproject.controller;

import com.group4.miniproject.annotation.BindingCheck;
import com.group4.miniproject.dto.AccountLoginRequestDto;
import com.group4.miniproject.dto.AccountRequestDTO;
import com.group4.miniproject.dto.ResponseDto;
import com.group4.miniproject.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class AccountController {
    private final AccountService accountService;

    @BindingCheck
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid AccountRequestDTO accountRequestDTO,
                                    BindingResult bindingResult) throws Exception {
        return new ResponseEntity<>(accountService.signUp(accountRequestDTO), HttpStatus.OK);
    }


    @BindingCheck
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> signIn(@RequestBody @Valid AccountLoginRequestDto accountRequestDTO,
                                              BindingResult bindingResult) {
        return accountService.signIn(accountRequestDTO);
    }
}
