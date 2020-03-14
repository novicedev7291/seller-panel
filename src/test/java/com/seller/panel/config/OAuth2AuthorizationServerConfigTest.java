package com.seller.panel.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.sql.DataSource;

@ExtendWith(MockitoExtension.class)
public class OAuth2AuthorizationServerConfigTest {

    @InjectMocks
    private OAuth2AuthorizationServerConfig oauthConfig;
    @Mock
    private DataSource dataSource;

    @Test
    public void shouldConfigureClientsDetailsServiceConfigurer() throws Exception {
        ClientDetailsServiceBuilder<?> builder = new ClientDetailsServiceBuilder<>();
        ClientDetailsServiceConfigurer clients = new ClientDetailsServiceConfigurer(builder);
        oauthConfig.configure(clients);
    }

    @Test
    public void shouldAuthorizationServerSecurityConfigurer() throws Exception {
        AuthorizationServerSecurityConfigurer configurer = new AuthorizationServerSecurityConfigurer();
        oauthConfig.configure(configurer);
    }

    @Test
    public void shouldAuthorizationServerEndpointsConfigurer() throws Exception{
        AuthorizationServerEndpointsConfigurer configurer = new AuthorizationServerEndpointsConfigurer();
        oauthConfig.configure(configurer);
    }

}
