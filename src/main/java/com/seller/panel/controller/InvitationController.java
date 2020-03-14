package com.seller.panel.controller;

import com.seller.panel.dto.InvitationRequest;
import com.seller.panel.handler.ExceptionHandler;
import com.seller.panel.service.MailService;
import com.seller.panel.util.EndPointConstants;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping
public class InvitationController {

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    private Environment env;

    @PostMapping(EndPointConstants.Invitation.INVITE)
    public ResponseEntity<Void> invite(@NotNull @RequestBody InvitationRequest request) {
        if(StringUtils.isBlank(request.getEmail()))
            throw exceptionHandler.getException("SP-1");
        String token = jwtTokenUtil.generateToken(request.getEmail());
        mailService.sendEmail(request.getEmail(), "Welcome To Seller Panel", "http://localhost:"+
                env.getProperty("server.port")+EndPointConstants.Registration.REGISTER+"/"+jwtTokenUtil.getJtiFromToken(token));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}