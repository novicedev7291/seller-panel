package com.seller.panel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.InvitationRequest;
import com.seller.panel.dto.LoginRequest;
import com.seller.panel.util.EndPointConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerIT extends BaseControllerIT {

    @Autowired
    private MockMvc mvc;

    @BeforeTestClass
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldReturn400WithMessageKeySP3() throws Exception {
        this.mvc.perform(post(EndPointConstants.Login.LOGIN).content(asJsonString(new LoginRequest()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageKey").value("SP-3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Provide username"));
    }

    @Test
    public void shouldReturn400WithMessageKeySP4() throws Exception {
        this.mvc.perform(post(EndPointConstants.Login.LOGIN).content(asJsonString(new LoginRequest(TestDataMaker.EMAIL1, StringUtils.EMPTY)))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageKey").value("SP-4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Provide password"));
    }

    @Test
    public void shouldReturn400WithMessageKeySP5() throws Exception {
        this.mvc.perform(post(EndPointConstants.Login.LOGIN)
                .content(asJsonString(new LoginRequest(TestDataMaker.EMAIL2,
                        TestDataMaker.PASSWORD)))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageKey").value("SP-5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid username and password"));
    }

    @Test
    public void shouldLoginWith201() throws Exception {
        this.mvc.perform(post(EndPointConstants.Login.LOGIN).content(asJsonString(new LoginRequest(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD)))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

