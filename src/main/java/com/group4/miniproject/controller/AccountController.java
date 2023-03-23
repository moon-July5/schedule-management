package com.group4.miniproject.controller;

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

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid AccountRequestDTO accountRequestDTO,
                         BindingResult bindingResult,Model model) throws Exception {
        if(bindingResult.hasErrors()){
            log.info("has error.......");
            return ResponseEntity.status(400).body(bindingResult.getAllErrors());
        }
        try {
            AccountResponseDTO result = accountService.signUp(accountRequestDTO);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

}
