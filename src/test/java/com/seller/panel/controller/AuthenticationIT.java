package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.util.EndPointConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class AuthenticationIT extends BaseControllerIT {

    private static final String GRANT_CLIENT_ID = "sp-test-client-id";
    private static final String GRANT_TYPE = "password";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @BeforeTestClass
    public void setUp() {
        super.setUp();
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void shouldUnAuthenticate() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", GRANT_CLIENT_ID+1);

        ResultActions result
                = mvc.perform(post(EndPointConstants.OAuth.OAUTH_TOKEN)
                .params(params)
                .with(httpBasic("sp-test-client-id", TestDataMaker.PASSWORD))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldAuthenticate() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", GRANT_CLIENT_ID);
        params.add("username", TestDataMaker.EMAIL1);
        params.add("password", TestDataMaker.PASSWORD);

        ResultActions result
                = mvc.perform(post(EndPointConstants.OAuth.OAUTH_TOKEN)
                .params(params)
                .with(httpBasic("sp-test-client-id",TestDataMaker.PASSWORD))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        assertThat(jsonParser.parseMap(resultString).get("access_token").toString(), notNullValue());
    }
}
