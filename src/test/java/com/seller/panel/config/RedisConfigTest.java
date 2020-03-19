package com.seller.panel.config;

import com.seller.panel.data.TestDataMaker;
import com.seller.panel.util.AppConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedisConfigTest {

    @InjectMocks
    private RedisConfig redisConfig;

    @Mock
    private Environment env;

    @Test
    public void shouldConfigureJedisConnectionFactory() {
        when(env.getProperty(AppConstants.SPRING_REDIS_HOST)).thenReturn(TestDataMaker.REDIS_HOST);
        when(env.getProperty(AppConstants.SPRING_REDIS_PORT)).thenReturn(TestDataMaker.REDIS_PORT);
        when(env.getProperty(AppConstants.SPRING_REDIS_PASSWORD)).thenReturn(TestDataMaker.REDIS_PASSWORD);
        redisConfig.jedisConnectionFactory();
        verify(env, times(1)).getProperty(AppConstants.SPRING_REDIS_HOST);
        verify(env, times(1)).getProperty(AppConstants.SPRING_REDIS_PORT);
        verify(env, times(1)).getProperty(AppConstants.SPRING_REDIS_PASSWORD);
        verifyNoMoreInteractions(env);
    }

    @Test
    public void shouldConfigureRedisTemplate() {
        when(env.getProperty(AppConstants.SPRING_REDIS_HOST)).thenReturn(TestDataMaker.REDIS_HOST);
        when(env.getProperty(AppConstants.SPRING_REDIS_PORT)).thenReturn(TestDataMaker.REDIS_PORT);
        when(env.getProperty(AppConstants.SPRING_REDIS_PASSWORD)).thenReturn(TestDataMaker.REDIS_PASSWORD);
        RedisTemplate redisTemplate = redisConfig.redisTemplate();
        assertThat(redisTemplate.getConnectionFactory(), notNullValue());
        verify(env, times(1)).getProperty(AppConstants.SPRING_REDIS_HOST);
        verify(env, times(1)).getProperty(AppConstants.SPRING_REDIS_PORT);
        verify(env, times(1)).getProperty(AppConstants.SPRING_REDIS_PASSWORD);
        verifyNoMoreInteractions(env);
    }

}
