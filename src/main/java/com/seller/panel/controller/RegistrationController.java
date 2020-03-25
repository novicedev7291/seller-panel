package com.seller.panel.controller;

import com.seller.panel.dto.Registration;
import com.seller.panel.dto.RegistrationRequest;
import com.seller.panel.service.RegistrationService;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class RegistrationController extends BaseController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping(EndPointConstants.Registration.REGISTER)
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequest request) {
        Registration registration = new Registration();
        BeanUtils.copyProperties(request, registration);
        Long userId = registrationService.createCompanyAndUser(registration);
        Map<String, Object> claims = new HashMap<>();
        String token = jwtTokenUtil.generateToken(StringUtils.EMPTY, claims);
        httpServletRequest.setAttribute(AppConstants.TOKEN, token);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
