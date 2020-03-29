package com.seller.panel.controller;

import com.github.javafaker.App;
import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.CategoryRequest;
import com.seller.panel.dto.JoinRequest;
import com.seller.panel.model.Categories;
import com.seller.panel.repository.CategoryRepository;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerIT extends BaseControllerIT {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeTestClass
    public void setUp() {
        super.setUp();
        categoryRepository.deleteAll();
    }

    @Test
    public void shouldReturn400WithEmailMustNotBeEmpty() throws Exception {
        String token = fetchAccessToken();
        this.mvc.perform(post(EndPointConstants.Categories.CATEGORIES)
                .content(asJsonString(new CategoryRequest()))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(AppConstants.AUTHORIZATION, AppConstants.BEARER+token))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(AppConstants.MUSTNOTBEEMPTY));
    }

    @Test
    public void shouldCreateCategoryWithParent() throws Exception {
        String token = fetchAccessToken();
        CategoryRequest request = TestDataMaker.makeCategoryRequest();
        request.setParentId(NumberUtils.LONG_ONE);
        this.mvc.perform(post(EndPointConstants.Categories.CATEGORIES)
                .content(asJsonString(request))
                .header(TestDataMaker.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(AppConstants.AUTHORIZATION, AppConstants.BEARER+token))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parent.id").value(request.getParentId()));
    }

}
