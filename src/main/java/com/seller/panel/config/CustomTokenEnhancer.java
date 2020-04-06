package com.seller.panel.config;

import com.seller.panel.model.Companies;
import com.seller.panel.model.Users;
import com.seller.panel.repository.UserRepository;
import com.seller.panel.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Users user = userRepository.findByEmail(
                ((UserDetails) oAuth2Authentication.getPrincipal()).getUsername());
        Companies company = user.getCompany();
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put(AppConstants.COMPANY_ID, company.getId());
        additionalInfo.put("companyName", company.getName());
        additionalInfo.put("id", user.getId());
        additionalInfo.put("name", user.getName());
        additionalInfo.put("email", user.getEmail());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);

        return oAuth2AccessToken;
    }

}
