package com.group4.miniproject.service;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.dto.PrincipalDto;
import com.group4.miniproject.dto.ScheduleRequestDto;
import com.group4.miniproject.dto.ScheduleResponseDto;
import com.group4.miniproject.encrypt256.Encrypt256;
import com.group4.miniproject.repository.AccountRepository;
import com.group4.miniproject.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Transactional
@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AccountRepository accountRepository;

    private Encrypt256 encrypt256 = new Encrypt256();

    // 개인 연차/당직 조회
    public ScheduleResponseDto getSchedulesById(Long id) throws Exception {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("유효하지 않은 id 입니다."));

        return ScheduleResponseDto.from(encrypt256, account);
    }
    // 연차/당직 등록
    public boolean saveSchedule(ScheduleRequestDto scheduleRequestDto, PrincipalDto principalDto) {
        Optional<Account> account = accountRepository.findById(principalDto.getId());
        log.info("account = "+account);

        // 타입이 일정이면
        if(scheduleRequestDto.getScheduleType().getType().equals("PLAN")){
            if(scheduleRequestDto.getContent().equals(""))
                throw new IllegalArgumentException("일정 내용을 작성해 주세요!");
        }

        Long yearly = account.get().getYearly() - diff(scheduleRequestDto);
        log.info("yearly = "+yearly);

        // 타입이 연차면
        if(scheduleRequestDto.getScheduleType().getType().equals("YEARLY")){
            if(yearly < 0){
                throw new IllegalArgumentException("남은 연차가 없습니다!");
            }
        }

        Schedule schedule = scheduleRequestDto.toEntity(principalDto);
        scheduleRepository.save(schedule);
        account.get().setYearly(yearly);

        log.info("account yearly : "+account.get().getYearly());

        return true;
    }

    // 연차/당직 수정
    public boolean updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto, PrincipalDto principalDto){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다!"));

        if(!schedule.getAccount().getId().equals(principalDto.getId())){
            throw new AccessDeniedException("권한이 없습니다.");
        }

        Optional<Account> account = accountRepository.findById(principalDto.getId());

        log.info("account = "+account);


        // 타입이 일정이면
        if(scheduleRequestDto.getScheduleType().getType().equals("PLAN")){
            if(scheduleRequestDto.getContent().equals(""))
                throw new IllegalArgumentException("일정 내용을 작성해 주세요!");
        }

        // 수정하기 전 날짜 차이
        int prevDiff = Period.between(schedule.getStartDate().toLocalDate(),
                schedule.getEndDate().toLocalDate()).getDays()+1;

        log.info("prevDiff = "+prevDiff);
        // 수정한 후 날짜 차이
        int currentDiff = diff(scheduleRequestDto);
        log.info("currentDiff = "+currentDiff);

        Long yearly = account.get().getYearly() + (prevDiff - currentDiff);

        log.info("yearly = "+yearly);

        // 타입이 연차면
        if(scheduleRequestDto.getScheduleType().getType().equals("YEARLY")){
            if(yearly < 0){
                throw new IllegalArgumentException("남은 연차가 없습니다!");
            }
        }

        if(!schedule.getStartDate().equals(scheduleRequestDto.getStartDate())){
            schedule.setStartDate(scheduleRequestDto.getStartDate());
        }

        if(!schedule.getEndDate().equals(scheduleRequestDto.getEndDate())){
            schedule.setEndDate(scheduleRequestDto.getEndDate());
        }

        if(scheduleRequestDto.getScheduleType().getType().equals("PLAN")){
            if(!schedule.getContent().equals(scheduleRequestDto.getContent())){
                schedule.setContent(scheduleRequestDto.getContent());
            }
        }

        account.get().setYearly(yearly);

        log.info("account yearly : "+account.get().getYearly());

        log.info("schedule = "+schedule.toString());
        return true;
    }

    // 연차 / 당직 삭제
    public boolean deleteSchedule(Long id, PrincipalDto principal){
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("일정이 존재하지 않습니다!"));

        if(!schedule.getAccount().getId().equals(principal.getId())){
            throw new AccessDeniedException("권한이 없습니다.");
        }

        // 연차일 경우
        if(schedule.getType().getType().equals("YEARLY")){
            Optional<Account> account = accountRepository.findById(principal.getId());

            Long yearly = account.get().getYearly() + Period.between(schedule.getStartDate().toLocalDate()
                    ,schedule.getEndDate().toLocalDate()).getDays()+1;

            account.get().setYearly(yearly);
        }

        scheduleRepository.deleteById(id);
        return true;
    }

    // 날짜 차이 계산
    public int diff(ScheduleRequestDto dto){
        LocalDateTime start = dto.getStartDate().toLocalDate().atStartOfDay();
        LocalDateTime end = dto.getEndDate().toLocalDate().atStartOfDay();

        Period diff = Period.between(start.toLocalDate(), end.toLocalDate());

        return diff.getDays()+1;

    }
}
