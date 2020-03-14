package com.seller.panel.controller;

import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.service.MailService;
import com.seller.panel.util.EndPointConstants;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping
public class RegistrationController {

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    private Environment env;

    @GetMapping(EndPointConstants.Registration.REGISTER)
    public ResponseEntity<Void> register(@NotNull @PathVariable("id") String id) {
        if(StringUtils.isBlank(id))
            throw exceptionHandler.getException("SP-2");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}