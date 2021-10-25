package com.aircraft.codelab.labcore.auth.jwt;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * jwt token  generator
 *
 * @author felord.cn
 * @since 2021 /3/27 13:33
 */
@Slf4j
@AllArgsConstructor
public class JwtTokenGenerator {
    private final JwtProperties.Claims claims;
    private final JwtTokenStorage jwtTokenStorage;
    private final JwtEncoder jwtEncoder;


    public OAuth2AccessTokenResponse tokenResponse(UserDetails userDetails) {
        JoseHeader joseHeader = JoseHeader.withAlgorithm(SignatureAlgorithm.RS256)
                .type("JWT").build();

        Instant issuedAt = Instant.now();
        Set<String> scopes = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Integer expiresAt = claims.getExpiresAt();

        String username = userDetails.getUsername();
        JwtClaimsSet sharedClaims = JwtClaimsSet.builder()
                // 签发者
                .issuer(claims.getIssuer())
                // 面向的用户群体
                .subject(claims.getSubject())
                // 接收jwt的一方 userid
                .audience(Collections.singletonList(username))
                .claim("scopes", scopes)
//                .expiresAt(issuedAt.plusSeconds(epochSecond))
                .issuedAt(issuedAt)
                .build();

        Jwt accessToken = jwtEncoder.encode(joseHeader, JwtClaimsSet.from(sharedClaims)
                .expiresAt(issuedAt.plusSeconds(expiresAt))
                .build());
        Jwt refreshToken = jwtEncoder.encode(joseHeader, sharedClaims);

        OAuth2AccessTokenResponse tokenResponse = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .expiresIn(expiresAt)
                .scopes(scopes)
                .refreshToken(refreshToken.getTokenValue()).build();
        return jwtTokenStorage.put(tokenResponse, username);
    }


}