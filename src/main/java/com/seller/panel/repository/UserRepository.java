package com.seller.panel.repository;

import com.seller.panel.model.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<Users, Long> {

    Users findByEmailAndCompanyId(String email, Long companyId);
    Users findByEmail(String email);
    Users findByIdAndCompanyId(Long id, Long companyId);
    List<Users> findAllByCompanyIdOrderByUpdatedOnDesc(Long companyId, Pageable pageable);

}
