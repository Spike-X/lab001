package com.aircraft.codelab.labcore;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

/**
 * 2021-10-11
 *
 * @author tao.zhang
 * @since 1.0
 */
public class JwtTest {
    private static final long TokenExpiration = 1000 * 60 * 2;

    private static final String SecretKey = "Kg6xIMaMwMAXKme/HGhY6megnd2FQ+p1VZBI9udYGnI=";
    private static final String PrivateKey = "";
    private static final String PublicKey = "";

    @Test
    void secretKeyHS256() {
        // 随机生成一个256bits的SecretKey
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //or HS384 or HS512
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("secretKey: " + secretString);
    }

    @Test
    void createTokenHS256() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretKey));
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
                .setExpiration(new Date(System.currentTimeMillis() + TokenExpiration))
                // Signature
                .signWith(key).compact();

        System.out.println("jwt token: " + jwtToken);
    }

    @Test
    void parseTokenHS256() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecretKey));
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    // SecretKey
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InNwaWtlIiwicm9sZSI6ImFkbWluIiwiYXZhdGFyIjoicy5qcGciLCJpc3MiOiJhaXJjcmFmdCIsImlhdCI6MTYzMzk0NDYwMCwiZXhwIjoxNjMzOTQ0NzIwfQ.LALcyUV8uFAXe7qyuOo0EIDh_u05XJ38G5qBnDnpdow");
            System.out.println(claimsJws);
        } catch (JwtException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void secretKeyRS256() {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256); //or RS384, RS512, PS256, PS384, PS512, ES256, ES384, ES512
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyString = Encoders.BASE64.encode(privateKey.getEncoded());
        System.out.println("privateKey: " + privateKeyString);
        System.out.println("privateKey: " + privateKey);

        PublicKey publicKey = keyPair.getPublic();
        String publicKeyString = Encoders.BASE64.encode(publicKey.getEncoded());
        System.out.println("publicKey: " + publicKeyString);
    }

    @Test
    void createTokenRS256() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(PrivateKey));
//        SecretKey key = Keys.hmacShaKeyFor(PrivateKey.getBytes(StandardCharsets.UTF_8));

        String jwtToken = Jwts.builder()
                // Header
                .setHeaderParam("alg", "RS256")
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
                .setExpiration(new Date(System.currentTimeMillis() + TokenExpiration))
                // Signature
                .signWith(key).compact();

        System.out.println("jwt token: " + jwtToken);

        SecretKey key1 = Keys.hmacShaKeyFor(Decoders.BASE64.decode(PublicKey));
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    // PublicKey
                    .setSigningKey(key1)
                    .build()
                    .parseClaimsJws(jwtToken);
            System.out.println(claimsJws);
        } catch (JwtException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void parseTokenRS256() {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(PublicKey));
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    // PublicKey
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws("eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InNwaWtlIiwicm9sZSI6ImFkbWluIiwiYXZhdGFyIjoicy5qcGciLCJpc3MiOiJhaXJjcmFmdCIsImlhdCI6MTYzMzk0NTQ2MiwiZXhwIjoxNjMzOTQ1NTgyfQ.pTjAEhGx8BgeekHrF-UfIUw2QV2zPxbvHIejDsHEiKkZ4LBzCy_hKFuHhXa6X3GVa5Ax4G--6aLQ3CPQw-6PJg");
            System.out.println(claimsJws);
        } catch (JwtException ex) {
            ex.printStackTrace();
        }
    }
}
