package com.seller.panel.repository;

import com.seller.panel.model.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<Users, Long> {

    Users findByEmailAndCompanyIdAndActive(String email, Long companyId, Boolean active);
    Users findByEmailAndActive(String email, Boolean active);
    Users findByEmail(String email);
    Users findByIdAndCompanyId(Long id, Long companyId);

}
