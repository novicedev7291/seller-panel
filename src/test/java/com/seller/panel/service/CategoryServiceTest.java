package com.seller.panel.service;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.model.Categories;
import com.seller.panel.repository.CategoryRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest extends BaseServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void shouldCreateCategoryWithoutParentCategory() {
        Categories category = TestDataMaker.makeCategories();
        when(categoryRepository.save(category)).thenReturn(category);
        Long id = categoryService.createCategory(category, category.getCompanyId());
        assertThat(category.getId(), equalTo(id));
        verify(categoryRepository, times(1)).save(category);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void shouldThrowInvalidParentCategory() {
        Categories category = TestDataMaker.makeCategories();
        category.setParentId(NumberUtils.LONG_ONE);
        when(categoryRepository.findByIdAndCompanyId(category.getParentId(), category.getCompanyId())).thenReturn(null);
        when(exceptionHandler.getException("SP-10")).thenReturn(new SellerPanelException("Invalid parent category"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            categoryService.createCategory(category, category.getCompanyId());
        });
        verify(categoryRepository, times(1))
                                        .findByIdAndCompanyId(category.getParentId(), category.getCompanyId());
        verify(exceptionHandler, times(1)).getException("SP-10");
        verifyNoMoreInteractions(categoryRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldCreateCategoryWithParentCategory() {
        Categories parentCategory = TestDataMaker.makeCategories();
        Categories category = TestDataMaker.makeCategories();
        category.setParentId(parentCategory.getId());
        when(categoryRepository.findByIdAndCompanyId(category.getParentId(), parentCategory.getCompanyId()))
                                                                                        .thenReturn(parentCategory);
        when(categoryRepository.save(category)).thenReturn(category);
        Long id = categoryService.createCategory(category, parentCategory.getCompanyId());
        assertThat(category.getId(), equalTo(id));
        verify(categoryRepository, times(1))
                                        .findByIdAndCompanyId(category.getParentId(), parentCategory.getCompanyId());
        verify(categoryRepository, times(1)).save(category);
        verifyNoMoreInteractions(categoryRepository);
    }

}
