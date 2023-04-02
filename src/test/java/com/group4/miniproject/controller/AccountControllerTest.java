package com.group4.miniproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group4.miniproject.config.SecurityConfig;

import com.group4.miniproject.dto.account.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @WithUserDetails("admin")
    @DisplayName("회원 일정 정보 검색")
    @Test
    public void AccountScheduleInfoSearchTest1() throws Exception {
        mvc.perform(get("/account/admin/2"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithUserDetails("admin")
    @DisplayName("이름으로 회원 검색")
    @Test
    public void AccountSearchTest1() throws Exception {
        mvc.perform(get("/account/admin/search?name=김고수"))
                .andExpect(status().isOk())
                .andDo(print());
    }

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
                .name("유현")
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

    @DisplayName("로그인 성공")
    @Test
    public void LoginTest() throws Exception {
        AccountLoginRequestDto accountLoginRequestDto = AccountLoginRequestDto.builder()
                .accountId("admin")
                .password("12345678")
                .build();

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(accountLoginRequestDto)))
                .andDo(print());
    }

    @DisplayName("회원 수정 성공")
    @Test
    public void ModifyTest() throws Exception {
//        테스트 할때는 "/account/update/accountId" 시큐리티 경로 수정
        AccountModifyRequestDTO accountModifyRequestDTO1 = AccountModifyRequestDTO.builder()
                .accountId("accountId")
                .password("132456789")
                .email("asdfg@adsg")
                .newPassword("123456789")
                .build();

        mvc.perform(post("/account/update/accountId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountModifyRequestDTO1)))
                .andDo(print());
        log.info("수정1완료------------------------------------------------------");

        AccountModifyRequestDTO accountModifyRequestDTO2 = AccountModifyRequestDTO.builder()
                .accountId("accountId")
                .password("123456789")
                .email("yuu@axd.com")
                .newPassword("132456789")
                .build();

        mvc.perform(post("/account/update/accountId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountModifyRequestDTO2)))
                .andDo(print());
        log.info("수정2완료(원상복구)----------------------------------------------------");
    }

    @DisplayName("회원 삭제 성공")
    @Test
    public void DeleteTest() throws Exception {
        AccountDeleteDTO accountDeleteDTO = AccountDeleteDTO.builder().accountId("accountId").build();
        mvc.perform(post("/account/delete/accountId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDeleteDTO)))
                .andDo(print());
    }

/*    @DisplayName("회원 검색 성공")
    @Test
    public void searchTest() throws Exception {
        AccountSearchRequestDTO accountSearchRequestDTO = AccountSearchRequestDTO.builder().name("이미나").build();
        mvc.perform(get("/account/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountSearchRequestDTO)))
                .andDo(print());
    }*/

}