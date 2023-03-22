package com.group4.miniproject.controller;

import com.group4.miniproject.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class AccountController {
    private final AccountService accountService;
//    @PostMapping("/signup")
//    public String signup(){
//
//        return ""
//    }

}
