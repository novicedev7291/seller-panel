package com.seller.panel.controller;

import com.seller.panel.dto.RegistrationResponse;
import com.seller.panel.util.EndPointConstants;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@RestController
@RequestMapping
public class RegistrationController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;
    
    @GetMapping(EndPointConstants.Registration.REGISTER)
    public ResponseEntity<RegistrationResponse> register(@NotNull @PathVariable("id") String id) {
        if(StringUtils.isBlank(id))
            throw getException("SP-2");
        String token = (String) redisTemplate.opsForValue().get(id);
        if(StringUtils.isBlank(token))
            throw new AccessDeniedException(getMessage("SP-6"));
        String email = jwtTokenUtil.getSubjectFromToken(token);
        String newToken = jwtTokenUtil.generateToken(email, new HashMap<>());
        httpServletRequest.setAttribute("token", newToken);
        return ResponseEntity.status(HttpStatus.OK).body(new RegistrationResponse(email));
    }

}