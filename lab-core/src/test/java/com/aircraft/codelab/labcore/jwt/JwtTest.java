package com.aircraft.codelab.labcore.jwt;

import cn.hutool.core.codec.Base64;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.util.Date;

/**
 * 2021-10-10
 *
 * @author tao.zhang
 * @since 1.0
 */
public class JwtTest {
    private static final long tokenExpiration = 1000 * 60;

    private static final String tokenSecret = "root";

    @Test
    void tokenTest() {
        String base64EncodedSecretKey = Base64.encode(tokenSecret);
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
//        Key key = Keys.hmacShaKeyFor(keyBytes);
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //or HS384 or HS512

        String jwtToken = Jwts.builder()
                // Header
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                // Payload 自定义信息
                .claim("username", "spike")
                .claim("role", "admin")
                .claim("avatar", "s.jpg")
                // Payload 默认信息
                //签发者
                .setIssuer("aircraft")
                // 签发时间
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                // Signature
                .signWith(key).compact();
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256); //or RS384, RS512, PS256, PS384, PS512, ES256, ES384, ES512

        // {"username":"spike","role":"admin","avatar":"s.jpg","iss":"aircraft","iat":1633882306,"exp":1633882366}
        System.out.println("token:" + jwtToken);
    }
}
