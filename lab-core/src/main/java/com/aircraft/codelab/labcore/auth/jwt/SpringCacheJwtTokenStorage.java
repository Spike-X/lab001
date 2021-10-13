package com.aircraft.codelab.labcore.auth.jwt;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

/**
 * @author felord.cn
 * @since 2021/3/27 12:54
 */
public class SpringCacheJwtTokenStorage implements JwtTokenStorage {
    private static final String TOKEN_CACHE = "usrTkn";


    @CachePut(value = TOKEN_CACHE, key = "#userId")
    @Override
    public OAuth2AccessTokenResponse put(OAuth2AccessTokenResponse tokenResponse, String userId) {
        return tokenResponse;
    }

    @CacheEvict(value = TOKEN_CACHE, key = "#userId")
    @Override
    public void expire(String userId) {

    }

    @Cacheable(value = TOKEN_CACHE, key = "#userId")
    @Override
    public OAuth2AccessTokenResponse get(String userId) {
        return null;
    }
}
