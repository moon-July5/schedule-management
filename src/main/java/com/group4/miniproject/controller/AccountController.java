package com.group4.miniproject.controller;

import com.group4.miniproject.annotation.BindingCheck;
import com.group4.miniproject.dto.AccountRequestDTO;
import com.group4.miniproject.dto.AccountResponseDTO;
import com.group4.miniproject.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@Log4j2
@RestController
public class AccountController {
    private final AccountService accountService;

    @BindingCheck
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid AccountRequestDTO accountRequestDTO,
                         BindingResult bindingResult,Model model) throws Exception {
            accountService.IdUniqueCheck(accountRequestDTO.getAccountId());
            accountService.NameCheck(accountRequestDTO.getName());
            Long id =accountService.EmailCheck(accountRequestDTO.getName(),accountRequestDTO.getEmail());
            accountService.AlreadySignUpCheck(id);
            AccountResponseDTO result= accountService.SignUp(id,accountRequestDTO);
            return ResponseEntity.ok(result);
    }

}
