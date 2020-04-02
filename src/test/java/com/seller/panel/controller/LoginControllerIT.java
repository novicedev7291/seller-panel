package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.LoginRequest;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerIT extends BaseControllerIT {

    @BeforeTestClass
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldReturn400WithEmailMustNotBeEmpty() throws Exception {
        this.mvc.perform(post(EndPointConstants.Login.LOGIN).content(asJsonString(new LoginRequest()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(AppConstants.MUSTNOTBEEMPTY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(AppConstants.MUSTNOTBEEMPTY));
    }

    @Test
    public void shouldReturn400WithMessageKeySP5() throws Exception {
        this.mvc.perform(post(EndPointConstants.Login.LOGIN)
                .content(asJsonString(new LoginRequest(TestDataMaker.EMAIL2,
                        TestDataMaker.PASSWORD)))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.generic").value("Invalid username or password"));
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

}

