package com.seller.panel.service;

import com.seller.panel.data.TestDataMaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

    @InjectMocks
    private MailService mailService;

    @Mock
    private JavaMailSender mailSender;

    @Test
    public void shouldSendMail() {
        SimpleMailMessage simpleMailMessage = TestDataMaker.makeSimpleMailMessage();
        doNothing().when(mailSender).send(simpleMailMessage);
        mailService.sendEmail(simpleMailMessage.getTo()[0],
                simpleMailMessage.getSubject(), simpleMailMessage.getText());
        verify(mailSender, times(1)).send(simpleMailMessage);
        verifyNoMoreInteractions(mailSender);
    }

}
