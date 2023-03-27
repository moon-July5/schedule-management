package com.group4.miniproject.service;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.dto.PrincipalDto;
import com.group4.miniproject.dto.ScheduleRequestDto;
import com.group4.miniproject.repository.AccountRepository;
import com.group4.miniproject.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@Log4j2
@Transactional
@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AccountRepository accountRepository;

    // 일정 / 당직 등록
    public boolean saveSchedule(ScheduleRequestDto scheduleRequestDto, PrincipalDto principalDto)
            throws Exception {
        Optional<Account> account = accountRepository.findById(principalDto.getId());
        log.info("account = "+account);

        // 타입이 일정이면
        if(scheduleRequestDto.getScheduleType().getType().equals("PLAN")){
            if(scheduleRequestDto.getContent().equals(""))
                throw new IllegalArgumentException("일정 내용을 작성해주세요!");
        }

        Long yearly = account.get().getYearly() - diff(scheduleRequestDto, scheduleRequestDto.getStartDate(), scheduleRequestDto.getEndDate());
        log.info("yearly = "+yearly);

        // 타입이 연차면
        if(scheduleRequestDto.getScheduleType().getType().equals("YEARLY")){
            if(yearly < 0){
                throw new IllegalArgumentException("남은 연차가 없습니다!");
            }
        }

        if(account.isEmpty()){
            throw new IllegalAccessException("잘못된 접근 권한입니다!");
        }

        Schedule schedule = scheduleRequestDto.toEntity(principalDto);
        scheduleRepository.save(schedule);
        account.get().setYearly(yearly);

        log.info("account yearly : "+account.get().getYearly());

        return true;
    }

    // 날짜 차이 계산
    public int diff(ScheduleRequestDto dto, LocalDateTime start, LocalDateTime end){
        start = dto.getStartDate().toLocalDate().atStartOfDay();
        end = dto.getEndDate().toLocalDate().atStartOfDay();

        Period diff = Period.between(start.toLocalDate(), end.toLocalDate());

        return diff.getDays();

    }
}
