package com.codefactory.delivery.user.presentation.controller;

import com.codefactory.delivery.user.test.MockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @MockUser
    void test1() throws Exception {
        mockMvc.perform(get("/v1/user/profile"))
                .andDo(print());
    }

    @Test
    @MockUser(roles = {"USER", "ADMIN"})
    void test2() throws Exception {
        mockMvc.perform(get("/v1/user/profile/test"))
                .andDo(print());
    }
}