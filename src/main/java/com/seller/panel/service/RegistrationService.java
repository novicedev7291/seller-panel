package com.seller.panel.service;

import com.seller.panel.dto.Registration;
import com.seller.panel.model.Companies;
import com.seller.panel.model.Users;
import com.seller.panel.repository.CompanyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService extends BaseService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    @Transactional
    public Long createCompanyAndUser(Registration request) {
        Long companyId = companyRepository
                .save(new Companies(request.getCompanyName())).getId();
        if(!request.getPassword().equals(request.getConfirmPassword()))
            throw getException("SP-3");
        Users user = new Users();
        BeanUtils.copyProperties(request, user);
        user.setCompanyId(companyId);
        return userService.createUser(user);
    }

}
