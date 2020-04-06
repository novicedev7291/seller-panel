package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.JoinRequest;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JoinControllerIT extends BaseControllerIT {

    @Test
    public void shouldReturn400WithEmailMustNotBeEmpty() throws Exception {
        this.mvc.perform(post(EndPointConstants.Join.JOIN).content(asJsonString(new JoinRequest()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(AppConstants.MUSTNOTBEEMPTY));
    }

    @Test
    public void shouldReturn400WithEmailInvalid() throws Exception {
        this.mvc.perform(post(EndPointConstants.Join.JOIN).content(asJsonString(new JoinRequest(TestDataMaker.WRONG_EMAIL)))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(AppConstants.INVALID));
    }

}

