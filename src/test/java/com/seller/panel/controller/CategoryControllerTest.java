package com.seller.panel.controller;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.dto.CategoryRequest;
import com.seller.panel.dto.CategoryResponse;
import com.seller.panel.dto.UserRequest;
import com.seller.panel.dto.UserResponse;
import com.seller.panel.model.Categories;
import com.seller.panel.model.Users;
import com.seller.panel.service.CategoryService;
import com.seller.panel.util.AppConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest extends BaseControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Test
    public void shouldCreateCategory() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        CategoryRequest actual = TestDataMaker.makeCategoryRequest();
        when(categoryService.createCategory(any(Categories.class), anyLong())).thenReturn(NumberUtils.LONG_ONE);
        when(this.request.getAttribute(AppConstants.ADDITIONAL_INFO)).thenReturn(TestDataMaker.makeAdditionalInfo());
        ResponseEntity<CategoryResponse> responseEntity = categoryController.createCategories(actual);
        assertThat(HttpStatus.CREATED.value(), equalTo(responseEntity.getStatusCodeValue()));
        CategoryResponse expected = responseEntity.getBody();
        assertThat(NumberUtils.LONG_ONE, equalTo(expected.getId()));
        assertThat(actual.getName(), equalTo(expected.getName()));
        verify(categoryService, times(1)).createCategory(any(Categories.class), anyLong());
        verify(this.request, times(1)).getAttribute(AppConstants.ADDITIONAL_INFO);
        verifyNoMoreInteractions(categoryService);
        verifyNoMoreInteractions(this.request);
    }
}
