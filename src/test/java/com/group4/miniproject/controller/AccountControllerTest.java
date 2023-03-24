package com.group4.miniproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group4.miniproject.config.SecurityConfig;
import com.group4.miniproject.dto.AccountRequestDTO;
import com.group4.miniproject.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;


    @Autowired
    private WebApplicationContext context;

    /*
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
     */
    @Test
    public void AccountControllerTest() throws Exception {
        AccountRequestDTO accountRequestDTO =AccountRequestDTO.builder()
                .name("유지현")
                .accountId("accountId")
                .email("yuu@axd.com")
                .password("132456789")
                .build();

//        mvc.perform(post("/signup")
//                        .content(body) //HTTP Body에 데이터를 담는다
//                        .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
//                )
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().string("1"));


                mvc.perform(
                        post("/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(accountRequestDTO)))
                        .andExpect(status().isOk())
                        .andDo(print());
    }

}

/*
"{"
                                                + "  \"email\" : \"yuu@axd.com\", "
                                                + "  \"password\" : \"1234\", "
                                                + "  \"name\": \"유지현\", "
                                                + "  \"accountId\": \"accountId\" "
                                                + "}")
 */