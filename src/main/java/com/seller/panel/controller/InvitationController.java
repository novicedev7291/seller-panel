package com.seller.panel.controller;

import com.seller.panel.dto.InvitationRequest;
import com.seller.panel.service.MailService;
import com.seller.panel.util.AppConstants;
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
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class InvitationController extends BaseController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    private Environment env;

    @PostMapping(EndPointConstants.Invitation.INVITE)
    public ResponseEntity<Void> invite(@NotNull @RequestBody InvitationRequest request) {
        if(StringUtils.isBlank(request.getEmail()))
            throw getException("SP-1");
        String token = jwtTokenUtil.generateToken(request.getEmail(), new HashMap<>());

        String registerUrl = env.getProperty(AppConstants.UI_REGISTER_URL);
        MessageFormat mf = new MessageFormat(registerUrl);

        mailService.sendEmail(request.getEmail(), "Welcome To Seller Panel",
                mf.format(new Object[] {jwtTokenUtil.getJtiFromToken(token)}));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}