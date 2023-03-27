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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/schedule")
@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 등록 로직
    @AuthCheck
    @BindingCheck
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto,
                                  @AuthenticationPrincipal PrincipalDto principal,
                                  BindingResult bindingResult) throws Exception {

        boolean result = scheduleService.saveSchedule(scheduleRequestDto, principal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 수정 로직
    @AuthCheck
    @BindingCheck
    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @AuthenticationPrincipal PrincipalDto principal,
                                    @RequestBody @Valid ScheduleRequestDto scheduleRequestDto,
                                    BindingResult bindingResult) {

        boolean result = scheduleService.updateSchedule(id, scheduleRequestDto, principal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 삭제 로직
    @AuthCheck
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal PrincipalDto principalDto){

        boolean result = scheduleService.deleteSchedule(id, principalDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
