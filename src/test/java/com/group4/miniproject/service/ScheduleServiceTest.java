package com.group4.miniproject.service;

import com.group4.miniproject.dto.schedule.ScheduleTodayRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;
//    @Test
//    public void 투데이당직체크테스트(){
//        log.info(scheduleService.checkToday(LocalDateTime.of(2023,04,04,18,00,00)
//        ,LocalDateTime.of(2023,04,05,06,00,00)
//        ,LocalDateTime.of(2023,04,04,4,00,00)));
//    }
//    @Test
//    public void 투데이당직체크실패테스트(){
//        log.info(scheduleService.checkToday(LocalDateTime.of(2023,04,03,18,00,00)
//        ,LocalDateTime.of(2023,04,04,06,00,00)
//        ,LocalDateTime.of(2023,04,00,02,00,00)));
//    }

//    @Test
//    public void 투데이당직테스트(){
//        ScheduleTodayRequestDTO scheduleTodayRequestDTO = ScheduleTodayRequestDTO.builder()
//                .end_date(LocalDateTime.of(2023,04,05,01,00,00))
//                .start_date(LocalDateTime.of(2023,04,05,01,00,00))
//                .build();
//        log.info(scheduleService.getTodayDuty(scheduleTodayRequestDTO));

//    }

}
