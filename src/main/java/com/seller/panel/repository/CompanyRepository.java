package com.seller.panel.repository;

import com.seller.panel.model.Companies;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Companies, Long> {

}
