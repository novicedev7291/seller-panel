package com.seller.panel.repository;

import com.seller.panel.model.Categories;
import com.seller.panel.model.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Categories, Long> {

    Categories findByIdAndCompanyId(Long id, Long companyId);

}
