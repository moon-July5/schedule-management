package com.group4.miniproject.controller;


import com.group4.miniproject.annotation.AuthCheck;
import com.group4.miniproject.annotation.BindingCheck;
import com.group4.miniproject.dto.PrincipalDto;
import com.group4.miniproject.dto.schedule.ScheduleRequestDto;
import com.group4.miniproject.dto.schedule.ScheduleTodayRequestDTO;
import com.group4.miniproject.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/schedule")
@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 전체 일정 조회
    @GetMapping("/all")
    public ResponseEntity<?> allSchedule(){
        return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
    }

    // 등록 로직
    @AuthCheck
    @BindingCheck
    @PostMapping("/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal PrincipalDto principal,
                                  @RequestBody @Valid ScheduleRequestDto scheduleRequestDto,
                                  BindingResult bindingResult) throws Exception {

        boolean result = scheduleService.saveSchedule(scheduleRequestDto, principal);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // 유저에 따른 일정/당직 조회 로직
    @AuthCheck
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(required = false) Long id) throws Exception {

        return new ResponseEntity<>(scheduleService.getSchedulesById(id), HttpStatus.OK);
    }

    // 수정 로직
    @AuthCheck
    @BindingCheck
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
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
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal PrincipalDto principal){

        boolean result = scheduleService.deleteSchedule(id, principal);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @AuthCheck
    @GetMapping("/today-duty")
    public ResponseEntity<?> todayDuty(@RequestBody ScheduleTodayRequestDTO scheduleTodayRequestDTO){
        return new ResponseEntity<>(scheduleService.getTodayDuty(scheduleTodayRequestDTO),HttpStatus.OK);
    }
}
