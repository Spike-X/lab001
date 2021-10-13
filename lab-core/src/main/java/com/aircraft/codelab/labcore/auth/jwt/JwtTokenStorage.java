package com.aircraft.codelab.labcore.auth.jwt;

import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

/**
 * jwt token storage
 *
 * @author Dax
 * @since 13 :25  2018/9/21
 */
public interface JwtTokenStorage {


    /**
     * Put jwt token pair.
     *
     * @param tokenResponse the token response
     * @param userId        the user id
     * @return the jwt token pair
     */
    OAuth2AccessTokenResponse put(OAuth2AccessTokenResponse tokenResponse, String userId);


    /**
     * Expire.
     *
     * @param userId the user id
     */
    void expire(String userId);


    /**
     * Get jwt token pair.
     *
     * @param userId the user id
     * @return the jwt token pair
     */
    OAuth2AccessTokenResponse get(String userId);

}
