package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RegisterControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturn200StatusCode() throws Exception {
        this.mvc.perform(get(TestDataMaker.REGISTER_ENDPOINT)).andExpect(status().isOk());
    }

}

