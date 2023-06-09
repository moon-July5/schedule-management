package com.group4.miniproject.controller;

import com.group4.miniproject.annotation.BindingCheck;
import com.group4.miniproject.dto.account.*;
import com.group4.miniproject.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AccountLoginResponseDTO> signIn(@RequestBody @Valid AccountLoginRequestDto accountRequestDTO,
                                                          BindingResult bindingResult) {
        return accountService.signIn(accountRequestDTO);
    }

    @BindingCheck
    @PostMapping("/account/update/{accountId}")
    public ResponseEntity<?> modify(@RequestBody @Valid AccountModifyRequestDTO accountModifyRequestDTO, BindingResult bindingResult) throws Exception {

        return new ResponseEntity<>(accountService.modify(accountModifyRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/account/delete/{accountId}")
    public ResponseEntity<?> delete(@RequestBody AccountDeleteDTO accountDeleteDTO) throws Exception {
        return new ResponseEntity<>(accountService.delete(accountDeleteDTO), HttpStatus.OK);
    }
    @GetMapping("/account/admin/search")
    public ResponseEntity<?> getAccountInfo(@RequestParam(required = false) String name) throws Exception {
        return new ResponseEntity<>(accountService.getAccountScheduleInfo(name) ,HttpStatus.OK);
    }

/*    @GetMapping("/account/admin/{id}")
    public ResponseEntity<?> getAccountScheduleInfo(@PathVariable Long id) throws Exception {

        return new ResponseEntity<>(accountService.getAccountScheduleInfo(id), HttpStatus.OK);
    }*/

    @PostMapping("/account/admin/role/{id}")
    public ResponseEntity<?> setAccountRole(@PathVariable Long id, @RequestBody AccountRoleRequestDTO accountRoleRequestDTO){

        return new ResponseEntity<>(accountService.setAccountRole(id, accountRoleRequestDTO), HttpStatus.OK);
    }

}
