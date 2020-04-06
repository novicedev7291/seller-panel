package com.seller.panel.util;

import org.apache.commons.lang3.math.NumberUtils;

public final class AppConstants {

    private AppConstants() {
        //
    }

    public static final String SERVER_PORT = "server.port";
    public static final String UI_INVITE_URL = "ui.invite.url";
    public static final String JWT_EXPIRATION = "jwt.expiration";
    public static final String JWT_SECRET = "jwt.secret";
    public static final String HEADER_PAYLOAD = "header.payload";
    public static final String SIGNATURE = "signature";
    public static final String JOIN_TOKEN_EXPIRY = "join.token.expiry";
    public static final String SPRING_REDIS_HOST =  "spring.redis.host";
    public static final String SPRING_REDIS_PORT =  "spring.redis.port";
    public static final String SPRING_REDIS_PASSWORD =  "spring.redis.password";
    public static final String GENERIC =  "generic";
    public static final String MUSTNOTBEEMPTY = "Must not be empty";
    public static final String INVALID = "invalid";
    public static final String VALID_PASSWORD = "xJ3!dij50";
    public static final String ADDITIONAL_INFO = "additionalInfo";
    public static final String INVITE = "invite";
    public static final String BEARER = "bearer ";
    public static final String AUTHORIZATION = "authorization";
    public static final String TOKEN = "token";
    public static final String COMPANY_ID = "companyId";
    public static final Integer DEFAULT_PAGE = NumberUtils.INTEGER_ZERO;
    public static final Integer DEFAULT_SIZE = 40;


}
