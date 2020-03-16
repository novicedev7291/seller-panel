package com.seller.panel.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Autowired
    private Environment env;

    public String generateToken(String subject, Map<String, Object> claims) {
        claims.put("jti", UUID.randomUUID().toString());
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Long.parseLong(env.getProperty(AppConstants.JWT_EXPIRATION)),
                        ChronoUnit.MILLIS)))
                .signWith(SignatureAlgorithm.HS512, env.getProperty(AppConstants.JWT_SECRET)).compact();
    }

    public Boolean validateToken(String token, String subject) {
        return getSubjectFromToken(token).equals(subject);
    }

    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String getJtiFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(env.getProperty(AppConstants.JWT_SECRET))
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

}