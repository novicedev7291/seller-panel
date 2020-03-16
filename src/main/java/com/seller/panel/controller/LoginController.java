package com.seller.panel.controller;

import com.seller.panel.dto.InvitationRequest;
import com.seller.panel.dto.LoginRequest;
import com.seller.panel.dto.LoginResponse;
import com.seller.panel.model.Users;
import com.seller.panel.service.LoginService;
import com.seller.panel.service.UserService;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.text.MessageFormat;

@RestController
@RequestMapping
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping(EndPointConstants.Login.LOGIN)
    public ResponseEntity<LoginResponse> login(@NotNull @RequestBody LoginRequest request) {
        if(StringUtils.isBlank(request.getUsername()))
            throw getException("SP-3");
        if(StringUtils.isBlank(request.getPassword()))
            throw getException("SP-4");
        String token = loginService.validateDataAndGenerateToken(request);
        httpServletRequest.setAttribute("token", token);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(token));
    }
}
