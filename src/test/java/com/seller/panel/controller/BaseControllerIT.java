package com.seller.panel.controller;

import com.seller.panel.SellerPanelApplication;
import com.seller.panel.repository.CompanyRepository;
import com.seller.panel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;

@SpringBootTest(classes = SellerPanelApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeTestClass
    public void setUp() {
        // initial cleanup
        userRepository.deleteAll();
        companyRepository.deleteAll();
    }

}
