package com.group4.miniproject.controller;


import com.group4.miniproject.annotation.AuthCheck;
import com.group4.miniproject.annotation.BindingCheck;
import com.group4.miniproject.dto.PrincipalDto;
import com.group4.miniproject.dto.ScheduleRequestDto;
import com.group4.miniproject.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/schedule")
@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    @AuthCheck
    @BindingCheck
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto,
                                  @AuthenticationPrincipal PrincipalDto principal,
                                  BindingResult bindingResult) throws Exception {

        boolean result = scheduleService.saveSchedule(scheduleRequestDto, principal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
