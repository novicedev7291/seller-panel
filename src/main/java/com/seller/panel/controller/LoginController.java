package com.seller.panel.controller;

import com.seller.panel.dto.LoginRequest;
import com.seller.panel.dto.LoginResponse;
import com.seller.panel.model.Users;
import com.seller.panel.service.UserService;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import com.seller.panel.util.JwtTokenUtil;
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
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping(EndPointConstants.Login.LOGIN)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Users user = userService.authenticate(request.getEmail(), request.getPassword());
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("name", user.getName());
        additionalInfo.put("email", user.getEmail());
        additionalInfo.put("companyName", user.getCompany().getName());
        additionalInfo.put(AppConstants.COMPANY_ID, user.getCompanyId());
        additionalInfo.put("companyCode", user.getCompany().getCode());
        Map<String, Object> claims = new HashMap<>();
        claims.put("additionalInfo", additionalInfo);
        claims.put("user_name", user.getEmail());
        claims.put("scope", new Object[]{"read", "write"});
        String token = jwtTokenUtil.generateToken(user.getEmail(), claims);
        httpServletRequest.setAttribute(AppConstants.TOKEN, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(token));
    }

}
