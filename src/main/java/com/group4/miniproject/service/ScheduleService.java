package com.group4.miniproject.service;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.AccountRole;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
import com.group4.miniproject.dto.*;
import com.group4.miniproject.dto.account.AccountLoginResponseDTO;
import com.group4.miniproject.dto.schedule.*;
import com.group4.miniproject.util.Encrypt256;
import com.group4.miniproject.repository.AccountRepository;
import com.group4.miniproject.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Transactional
@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AccountRepository accountRepository;

    // 전체 일정 조회
    public ScheduleAllResponseDto getAllSchedules() throws Exception {
        List<Account> accountList =accountRepository.findAll();
        List<ScheduleAllResponseDto.UserData> result = new ArrayList<>();
        for(Account a : accountList) {
            if(!a.getSchedules().isEmpty()) {
                for(Schedule s : a.getSchedules()){
                    result.add(new ScheduleAllResponseDto.UserData(a,s));
                }
            }
        }

        return ScheduleAllResponseDto.builder().users(result).build();
    }

    // 개인 연차/당직 조회
    public ScheduleResponseDto getSchedulesById(Long id) throws Exception {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("유효하지 않은 id 입니다."));

        return ScheduleResponseDto.from( account);
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

        // 만약 다른 유저 것에 접근 시, 그리고 권한이 ROLE_ADMIN이 아닌 경우
        if((!schedule.getAccount().getId().equals(principalDto.getId()))
                && !accountRepository.findById(principalDto.getId()).get().getRoles().contains(AccountRole.ROLE_ADMIN)){

            throw new AccessDeniedException("권한이 없습니다.");
        }

        Optional<Account> account = accountRepository.findById(schedule.getAccount().getId());

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

        if(!schedule.getStartDate().equals(scheduleRequestDto.getStart_date())){
            schedule.setStartDate(scheduleRequestDto.getStart_date());
        }

        if(!schedule.getEndDate().equals(scheduleRequestDto.getEnd_date())){
            schedule.setEndDate(scheduleRequestDto.getEnd_date());
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

        if((!schedule.getAccount().getId().equals(principal.getId()))
                && !accountRepository.findById(principal.getId()).get().getRoles().contains(AccountRole.ROLE_ADMIN)){

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
        LocalDateTime start = dto.getStart_date().toLocalDate().atStartOfDay();
        LocalDateTime end = dto.getEnd_date().toLocalDate().atStartOfDay();

        Period diff = Period.between(start.toLocalDate(), end.toLocalDate());

        return diff.getDays()+1;

    }

    public List<ScheduleTodayResponseDTO> getTodayDuty(LocalDate start_date) {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<Account> accountList = new ArrayList<>();
        for (Schedule i : scheduleList) {
            if (checkToday(i.getStartDate().toLocalDate(), i.getEndDate().toLocalDate(), start_date)) {
                if (i.getType().equals(ScheduleType.DUTY))
                    accountList.add(accountRepository.findById(i.getAccount().getId()).get());
            }
        }
        List<ScheduleTodayResponseDTO> resultList = new ArrayList<>();
        if (accountList.isEmpty()) {
            throw new IllegalArgumentException("당직이 존재하지 않습니다.");
        }
        for (Account i : accountList) {
            resultList.add(
                    new ScheduleTodayResponseDTO().builder()
                            .name(i.getName())
                            .position(i.getPosition())
                            .department(i.getDepartment())
                            .duty(i.getDuty())
                            .build()

            );
        }
        Set<ScheduleTodayResponseDTO> set = new HashSet<>(resultList);
        List<ScheduleTodayResponseDTO> result= new ArrayList<>(set);
        return result;
    }
    private boolean checkToday(LocalDate start,LocalDate end,LocalDate today){

        // 시작날자  <  오늘 < 끝 or 시작=오늘 or 오늘 = 끝
        if((start.isAfter(today) && end.isBefore(today))||(start.isEqual(today))||(end.isEqual(today))){
            return true;
        }
        return false;

    }
}
