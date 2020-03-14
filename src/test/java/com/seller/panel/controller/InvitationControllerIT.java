package com.seller.panel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.InvitationRequest;
import com.seller.panel.util.EndPointConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InvitationControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturn400WithMessageKeySP1() throws Exception {
        this.mvc.perform(post(EndPointConstants.Invitation.INVITE).content(asJsonString(new InvitationRequest()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.messageKey").value("SP-1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Provide email address"));
    }

    @Test
    public void shouldReturn201AfterSendingMail() throws Exception {
        this.mvc.perform(post(EndPointConstants.Invitation.INVITE).content(asJsonString(new InvitationRequest(TestDataMaker.EMAIL1)))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

