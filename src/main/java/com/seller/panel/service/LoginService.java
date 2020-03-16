package com.seller.panel.service;

import com.seller.panel.dto.LoginRequest;
import com.seller.panel.model.Users;
import com.seller.panel.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService extends BaseService{

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String validateDataAndGenerateToken(LoginRequest loginRequest) {
        Users user = userService.validateUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getFullName());
        claims.put("email", user.getEmail());
        claims.put("companyName", user.getCompany().getName());
        claims.put("companyId", user.getCompanyId());
        claims.put("companyCode", user.getCompany().getCode());
        return jwtTokenUtil.generateToken(user.getEmail(), claims);
    }

}
