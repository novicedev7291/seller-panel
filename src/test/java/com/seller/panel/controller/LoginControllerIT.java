package com.seller.panel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.LoginRequest;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerIT extends BaseControllerIT {

    @Autowired
    private MockMvc mvc;

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @BeforeTestClass
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldReturn400WithMessageKeySP3() throws Exception {
        this.mvc.perform(post(EndPointConstants.Login.LOGIN).content(asJsonString(new LoginRequest()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageKey").value("SP-4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Username must not be empty"));
    }

    @Test
    public void shouldReturn400WithMessageKeySP4() throws Exception {
        this.mvc.perform(post(EndPointConstants.Login.LOGIN).content(asJsonString(new LoginRequest(TestDataMaker.EMAIL1, StringUtils.EMPTY)))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageKey").value("SP-5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password must not be empty"));
    }

    @Test
    public void shouldReturn400WithMessageKeySP5() throws Exception {
        this.mvc.perform(post(EndPointConstants.Login.LOGIN)
                .content(asJsonString(new LoginRequest(TestDataMaker.EMAIL2,
                        TestDataMaker.PASSWORD)))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageKey").value("SP-6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid username and password"));
    }

    @Test
    public void shouldLoginWithStatus201AndCorrectCookies() throws Exception {
        ResultActions resultActions = this.mvc.perform(post(EndPointConstants.Login.LOGIN).content(asJsonString(new LoginRequest(TestDataMaker.EMAIL1, TestDataMaker.PASSWORD)))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON));
        String httpPayload = resultActions.andReturn().getResponse().getCookie(AppConstants.HEADER_PAYLOAD).getValue();
        String signature = resultActions.andReturn().getResponse().getCookie(AppConstants.SIGNATURE).getValue();
        resultActions.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(httpPayload.concat(".").concat(signature)))
                .andExpect(MockMvcResultMatchers.cookie().value(AppConstants.HEADER_PAYLOAD, httpPayload))
                .andExpect(MockMvcResultMatchers.cookie().value(AppConstants.SIGNATURE, signature));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

