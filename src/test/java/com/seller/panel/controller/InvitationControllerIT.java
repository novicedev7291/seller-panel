package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.util.EndPointConstants;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InvitationControllerIT extends BaseControllerIT {

    @Test
    public void shouldThrowAccessDeniedException() throws Exception {
        this.mvc.perform(get(EndPointConstants.Invitation.INVITE, TestDataMaker.EMAIL1)
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.generic").value("Link expired, please join us again"));
    }

}
