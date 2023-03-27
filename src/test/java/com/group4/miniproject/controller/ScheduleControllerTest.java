package com.group4.miniproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group4.miniproject.config.SecurityConfig;
import com.group4.miniproject.domain.ScheduleType;
import com.group4.miniproject.dto.ScheduleRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@SpringBootTest
class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @DisplayName("연차/당직 등록 테스트")
    @WithUserDetails("admin")
    @Test
    public void saveScheduleTest1() throws Exception {
        ScheduleRequestDto request = ScheduleRequestDto.builder()
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1L))
                .scheduleType(ScheduleType.YEARLY)
                .build();

        mockMvc.perform(post("/schedule/save")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @DisplayName("연차/당직 일정 내용 테스트")
    @WithUserDetails("admin")
    @Test
    public void saveScheduleTest2() throws Exception {
        ScheduleRequestDto request = ScheduleRequestDto.builder()
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusHours(6))
                .scheduleType(ScheduleType.PLAN)
                //.content("")
                .content("작업중")
                .build();

        mockMvc.perform(post("/schedule/save")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @DisplayName("연차/당직 수정 테스트")
    @WithUserDetails("user2")
    @Test
    public void updateScheduleTest1() throws Exception {
        ScheduleRequestDto request = ScheduleRequestDto.builder()
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .scheduleType(ScheduleType.YEARLY)
                .build();

        mockMvc.perform(post("/schedule/update/4")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @DisplayName("연차/당직 수정 테스트")
    @WithUserDetails("user2")
    @Test
    public void updateScheduleTest2() throws Exception {
        ScheduleRequestDto request = ScheduleRequestDto.builder()
                .startDate(LocalDateTime.of(2023, 04, 18, 0, 0, 0))
                .endDate(LocalDateTime.of(2023, 04, 20, 0, 0, 0))
                .scheduleType(ScheduleType.PLAN)
                .content("수정 중")
                .build();

        mockMvc.perform(post("/schedule/update/6")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(status().is4xxClientError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public void localDateTimeTest() {
        ScheduleRequestDto request = ScheduleRequestDto.builder()
                .startDate(LocalDateTime.of(2023, 03, 29, 0, 0, 0))
                .endDate(LocalDateTime.of(2023, 03, 31, 0, 0, 0))
                .scheduleType(ScheduleType.PLAN)
                //.content("")
                .content("작업중")
                .build();

        System.out.println("request.getEndDate() = " + request.getEndDate());

        LocalDateTime start = request.getStartDate().toLocalDate().atStartOfDay();
        LocalDateTime end = request.getEndDate().toLocalDate().atStartOfDay();

        Period diff = Period.between(start.toLocalDate(), end.toLocalDate());

        System.out.println("diff.getDays() = " + diff.getDays());

    }

}