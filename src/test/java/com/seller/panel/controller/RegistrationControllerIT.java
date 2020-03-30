package com.seller.panel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.RegistrationRequest;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerIT extends BaseControllerIT {

    @BeforeTestClass
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldReturn400WithMustNotBeEmptyMessage() throws Exception {
        this.mvc.perform(post(EndPointConstants.Registration.REGISTER).content(asJsonString(new RegistrationRequest()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(AppConstants.MUSTNOTBEEMPTY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(AppConstants.MUSTNOTBEEMPTY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(AppConstants.MUSTNOTBEEMPTY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.confirmPassword").value(AppConstants.MUSTNOTBEEMPTY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(AppConstants.MUSTNOTBEEMPTY));
    }

    @Test
    public void shouldReturn400WithMustNotBeEmptyAndInvalidMessage() throws Exception {
        this.mvc.perform(post(EndPointConstants.Registration.REGISTER)
                .content(asJsonString(TestDataMaker.makeRegistrationRequestWithWrongEmail()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(AppConstants.MUSTNOTBEEMPTY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(AppConstants.INVALID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value(AppConstants.MUSTNOTBEEMPTY))
                .andExpect(MockMvcResultMatchers.jsonPath("$.confirmPassword").value(AppConstants.MUSTNOTBEEMPTY));
    }

    @Test
    public void shouldReturn400WithPasswordsAreNotEqualMessage() throws Exception {
        this.mvc.perform(post(EndPointConstants.Registration.REGISTER)
                .content(asJsonString(TestDataMaker.makeRegistrationRequestWithMismatchInPassword()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath(AppConstants.GENERIC).value("Passwords are not equal"));
    }

    @Test
    public void shouldRegister() throws Exception {
        this.mvc.perform(post(EndPointConstants.Registration.REGISTER)
                .content(asJsonString(TestDataMaker.makeRegistrationRequest()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}

