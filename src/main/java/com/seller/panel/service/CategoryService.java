package com.seller.panel.service;

import com.seller.panel.model.Categories;
import com.seller.panel.repository.CategoryRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService {

    @Autowired
    CategoryRepository categoryRepository;

    public Long createCategory(Categories categories, Long companyId) {
        categories.setCompanyId(companyId);
        Categories parent = null;
        if(categories.getParentId() != null)
            parent = getValidCategoryEntity(categories.getParentId(), categories.getCompanyId());
        categories.setParent(parent);
        return categoryRepository.save(categories).getId();
    }

    private Categories getValidCategoryEntity(Long id, Long companyId) {
        Categories category = categoryRepository.findByIdAndCompanyId(id, companyId);
        if(category == null)
            throw getException("SP-10");
        return category;
    }
}
