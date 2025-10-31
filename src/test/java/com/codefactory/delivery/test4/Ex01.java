package com.codefactory.delivery.test4;

import com.codefactory.delivery.user.presentation.dto.TokenRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class Ex01 {

    @Autowired
    ObjectMapper om;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("JWT 토큰 발급 테스트")
    void tokenGenerateTest() throws Exception {

        TokenRequest req = new TokenRequest("user004", "_aA123456");
        String body = om.writeValueAsString(req);

        mockMvc.perform(post("/v1/user/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)).andDo(print());
    }
}
