package com.seller.panel.controller;

import com.seller.panel.dto.LoginRequest;
import com.seller.panel.dto.LoginResponse;
import com.seller.panel.model.Users;
import com.seller.panel.service.UserService;
import com.seller.panel.util.EndPointConstants;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getFullName());
        claims.put("email", user.getEmail());
        claims.put("companyName", user.getCompany().getName());
        claims.put("companyId", user.getCompanyId());
        claims.put("companyCode", user.getCompany().getCode());
        String token = jwtTokenUtil.generateToken(user.getEmail(), claims);
        httpServletRequest.setAttribute("token", token);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponse(token));
    }

}
