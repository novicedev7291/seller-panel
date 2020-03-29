package com.seller.panel.controller;

import com.seller.panel.dto.CategoryRequest;
import com.seller.panel.dto.CategoryResponse;
import com.seller.panel.dto.KeyNameLong;
import com.seller.panel.model.Categories;
import com.seller.panel.service.CategoryService;
import com.seller.panel.util.EndPointConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(EndPointConstants.Categories.CATEGORIES)
    @PreAuthorize("isAuthorized('POST','/categories')")
    public ResponseEntity<CategoryResponse> createCategories(@Valid @RequestBody CategoryRequest request) {
        Categories category = new Categories();
        BeanUtils.copyProperties(request, category);
        Long id = categoryService.createCategory(category, getCompanyId());
        CategoryResponse response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        response.setId(id);
        response.setParent(category.getParent() != null ?
                    new KeyNameLong(category.getParentId(), category.getParent().getName()) : null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
