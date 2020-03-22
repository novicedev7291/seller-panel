package com.seller.panel.controller;

import com.github.javafaker.App;
import com.seller.panel.dto.JoinRequest;
import com.seller.panel.service.MailService;
import com.seller.panel.util.AppConstants;
import com.seller.panel.util.EndPointConstants;
import com.seller.panel.util.JwtTokenUtil;
import org.apache.commons.lang.StringUtils;
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
import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern pattern = Pattern.compile(AppConstants.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(request.getEmail());
        if(!matcher.matches())
            throw getException("SP-2");
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