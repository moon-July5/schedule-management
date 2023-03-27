package com.group4.miniproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group4.miniproject.config.SecurityConfig;
import com.group4.miniproject.dto.AccountRequestDTO;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@Import(SecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    @DisplayName("회원가입 성공")
    @Test
    public void AccountControllerTest() throws Exception {
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .name("유지현")
                .accountId("accountId")
                .email("yuu@axd.com")
                .password("132456789")
                .build();

                mvc.perform(post("/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(accountRequestDTO)))
                        .andExpect(status().isOk())
                        .andDo(print());
    }

    @DisplayName("회원가입 실패 : 이름 빈 칸 입력")
    @Test
    public void AccountControllerFailTest() throws Exception {
        AccountRequestDTO accountRequestDTO = AccountRequestDTO.builder()
                .name("")
                .accountId("accountId")
                .email("yuu@axd.com")
                .password("132456789")
                .build();

        mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountRequestDTO)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }


}