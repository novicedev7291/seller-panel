package com.seller.panel.controller;

import com.seller.panel.dto.JoinRequest;
import com.seller.panel.service.MailService;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import com.seller.panel.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public class JoinController extends BaseController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    private Environment env;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping(EndPointConstants.Join.JOIN)
    public ResponseEntity<Void> join(@Valid @RequestBody JoinRequest request) {
        String token = jwtTokenUtil.generateToken(request.getEmail(), new HashMap<>());

        String inviteUrl = env.getProperty(AppConstants.UI_INVITE_URL);
        MessageFormat mf = new MessageFormat(inviteUrl);
        String jti = jwtTokenUtil.getJtiFromToken(token);
        redisTemplate.opsForValue().set(jti, token);
        redisTemplate.expire(jti, Long.parseLong(env.getProperty(AppConstants.JOIN_TOKEN_EXPIRY)), TimeUnit.MILLISECONDS);
        mailService.sendEmail(request.getEmail(), "Welcome To Seller Panel",
                mf.format(new Object[] {jti}));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}