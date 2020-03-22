package com.seller.panel.service;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.exception.SellerPanelException;
import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.model.Companies;
import com.seller.panel.model.Users;
import com.seller.panel.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private UserService userService;

    @Mock
    private ExceptionHandler exceptionHandler;

    @Test
    public void shouldThrow400ForPasswordMismatch() {
        when(companyRepository.save(any(Companies.class)))
                .thenReturn(TestDataMaker.makeCompany());
        when(exceptionHandler.getException("SP-3")).thenReturn(new SellerPanelException("Passwords are not equal"));
        Assertions.assertThrows(SellerPanelException.class, () -> {
            registrationService.createCompanyAndUser(TestDataMaker.makeRegistrationWithMismatchInPassword());
        });
        verify(companyRepository, times(1)).save(any(Companies.class));
        verify(exceptionHandler, times(1)).getException("SP-3");
        verifyNoMoreInteractions(companyRepository);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    public void shouldCreateCompanyAndUser() {
        when(companyRepository.save(any(Companies.class)))
                .thenReturn(TestDataMaker.makeCompany());
        when(userService.createUser(any(Users.class))).thenReturn(TestDataMaker.makeUser().getId());
        registrationService.createCompanyAndUser(TestDataMaker.makeRegistration());
        verify(companyRepository, times(1)).save(any(Companies.class));
        verify(userService, times(1)).createUser(any(Users.class));
        verifyNoMoreInteractions(companyRepository);
        verifyNoMoreInteractions(userService);
    }
}
