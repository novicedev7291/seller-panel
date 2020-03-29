package com.seller.panel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.panel.SellerPanelApplication;
import com.seller.panel.data.TestDataMaker;
import com.seller.panel.repository.CategoryRepository;
import com.seller.panel.repository.CompanyRepository;
import com.seller.panel.repository.UserRepository;
import com.seller.panel.util.EndPointConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SellerPanelApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    protected MockMvc mvc;

    protected static final String GRANT_CLIENT_ID = "sp-test-client-id";
    protected static final String GRANT_TYPE = "password";

    @BeforeTestClass
    public void setUp() {
        // initial cleanup
        userRepository.deleteAll();
        companyRepository.deleteAll();
    }

    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected String fetchAccessToken() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", GRANT_CLIENT_ID);
        params.add("username", TestDataMaker.EMAIL1);
        params.add("password", TestDataMaker.PASSWORD);

        ResultActions result
                = mvc.perform(post(EndPointConstants.OAuth.OAUTH_TOKEN)
                .params(params)
                .with(httpBasic("sp-test-client-id",TestDataMaker.PASSWORD))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();

        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}
