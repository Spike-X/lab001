package com.aircraft.codelab.labcore.jwt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
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
        String key1 = "1234567890_1234567890_1234567890";
        // 1. 根据key生成密钥（会根据字节参数长度自动选择相应的 HMAC 算法）
//        SecretKey secretKey = Keys.hmacShaKeyFor(key1.getBytes());
//        SecretKey secretKey = new SecretKeySpec(key1.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        // 2. 根据随机数生成密钥
        // 随机生成一个256bits的SecretKey HS256：bit 长度要>=256，即字节长度>=32
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //or HS384 or HS512
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("secretKey: " + secretString);
    }

    @Test
    void tokenHS256() {
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
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    // SecretKey
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
            System.out.println(claimsJws);
        } catch (JwtException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void hutoolRsa() {
        // 1024位
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        PrivateKey aPrivate = pair.getPrivate();
        PublicKey aPublic = pair.getPublic();
        String privateKeyString = Base64.encode(aPrivate.getEncoded());
        String publicKeyString = Base64.encode(aPublic.getEncoded());
        System.out.println("privateKey: " + privateKeyString);
        System.out.println("publicKey: " + publicKeyString);
    }

    @Test
    void secretKeyRS256() {
        // 2048bit
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256); //or RS384, RS512, PS256, PS384, PS512, ES256, ES384, ES512
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyString = Encoders.BASE64.encode(privateKey.getEncoded());
        System.out.println("privateKey: " + privateKeyString);

        PublicKey publicKey = keyPair.getPublic();
        String publicKeyString = Encoders.BASE64.encode(publicKey.getEncoded());
        System.out.println("publicKey: " + publicKeyString);
    }

    @Test
    void createTokenRS256() {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256); //or RS384, RS512, PS256, PS384, PS512, ES256, ES384, ES512
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

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
                // Signature privateKey
                .signWith(privateKey).compact();

        System.out.println("jwt token: " + jwtToken);

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    // PublicKey
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(jwtToken);
            System.out.println(claimsJws);
        } catch (JwtException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void tokenRS256() {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256); //or RS384, RS512, PS256, PS384, PS512, ES256, ES384, ES512
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyString = Encoders.BASE64.encode(privateKey.getEncoded());
        System.out.println(DatatypeConverter.printHexBinary(privateKey.getEncoded()));
        System.out.println("privateKey: " + privateKeyString);

        PublicKey publicKey = keyPair.getPublic();
        String publicKeyString = Encoders.BASE64.encode(publicKey.getEncoded());
        System.out.println("publicKey: " + publicKeyString);
        SecretKey key1 = Keys.hmacShaKeyFor(Decoders.BASE64.decode(privateKeyString));
        SecretKey key2 = Keys.hmacShaKeyFor(Decoders.BASE64.decode(publicKeyString));
        String jwtToken = Jwts.builder()
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
                // Signature privateKey
                .signWith(key1).compact();
        System.out.println("jwt token: " + jwtToken);

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    // PublicKey
                    .setSigningKey(key2)
                    .build()
                    .parseClaimsJws(jwtToken);
            System.out.println(claimsJws);
        } catch (JwtException ex) {
            ex.printStackTrace();
        }
    }
}
