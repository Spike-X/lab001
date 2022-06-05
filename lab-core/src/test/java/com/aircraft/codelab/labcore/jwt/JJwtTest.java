package com.aircraft.codelab.labcore.jwt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/**
 * 2021-10-11
 *
 * @author tao.zhang
 * @since 1.0
 */
public class JJwtTest {
    private static final long TokenExpiration = 1000 * 60 * 2;

    private static final String SecretKey = "9PeyHpaQcpRpaaTdjH1ebmyJPcU4+82RbXFeHI+HA70=";
    private static final String PrivateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCaNeWtZ/GYmpSLxoz6D9qiSD3vq59REBmGZPQdIpeaiy6Oqyjx2sd3UcbO1KsaXCOKbjLtzQmBX9cvYsNGJqkTquCR3/IjSYwNLqwPoHBkbHAWTmUK5cU6H4FaK0ao6aYbHXkvRaNrFo377RnKxnR/++QK0We1yfQfZvgKJ1HLxo1wozyCaGIDSTnH6CFyswU+OcZ/iOu8QVvsIPd0Uuh0yY0ycUkrwolEWoDfxkU5CROpXlzDLk5/coAAqrWXaXtV+aQYalkvC8T9I5kM50ny61IwOwI2uVKjVVPd/uOhIBfsl0jHH4gDgY1vM3aHsggZcYB0pzXBUYqw0YJ6EmgfAgMBAAECggEAWXu+/AGd6qyAWB9tvMkoWBTNg961wucxuI+qK6HgEPiEmprtVnJaXn2gfHJhmxkuMmzBtsXMpzsC9ec8Yt+aKuR0DZHCwwExeJDFnQe/UDawT/FLpqgv6wtPFxCXnvp9vaw7ntes/T4IQ9CQr966yW1UJYbfxFgdBX/NnK62QyTacvcDl9WsAEyH9T1cGgIIjh9tupaS7xIkn6L++1tF1L8HKw+DNJ3dpz5VpYnsFvufXO44ZJ1lwO2qWm1lKh1EgoT/rGiqkWgQPpVFZSqmv40WTW1h3a3iHHkH3OCvtcgFQJygxtPLXEhJ+mIXTP00SuWBcxegRWaasos6sCPmUQKBgQDiOnyzHAqnls5a3eXUtrLoE12FzzSj9XYNp6xOEtyXDhni+0CAUV44W1GnNUv2XrNOMtdmeJJQy0s6uC5+7JKrkqzxTFQsiMdwcXJkhrLumTRY2UorxJLVIcZhCR0DfH6DKO/SHbRn0OqKx/26JHrIeu4ZAfwWqsNfNwbazIMn2QKBgQCugSpgc7FwJXT3H3MpRO+VdIhBZvjHj2YAmu8KNQm20GpQweLSqsO6u2FW2Xz4M/g5qXZKzWhj2TH1bJ9o3nR1CM7JjerDBZVnC/mOxDrqAeMl/okRBJ7XUW63A6aIGJHcwygk6K3ejmY0KQs4dz73xxtZjLaAlwYnssF8HxDMtwKBgQDBRQBFsD42ALNYk9wGxvK/K9QpPeUV8d0iO04MATctYaGh9c6cEWeyM+nlNLYpy6vZsbut0nIFzjf9+V3Xl1qSc6OGHhSHW28vU+xLj3wMNRywjyo7jBt5M/qb6cI8uT4H5ZBE7JW0X5oQaF0Oip3O6p7BBJ4N62Mv0/3qLPAbuQKBgQCiScO0YcQSDPPDY79BEThTYUXatT4GsajmRTCr6Hl5SEinhDlDPMa84cj78VYUqpheX+iAVpmPyo14tqVl7QSXVzKhJde/uXChnfRgPsSfnlO2oQquBgMHMldp4v48McVtbx3lfXM9uGb6eXVS9StdWhFobcHIwytZPhhNK8L+7wKBgQDPiYiDTtICgq9XO8Sl9+8GhKBoeGzUdmNlS6DQlBS2ukbcXwtyCwmkG/g5itaHzEOkzB4vaK6ySZjHJWNnTh0BAWubOwwGpP5HjRqcqvgWL7nnCRUICHv8kmySh6NgwoLTRID/eShLYmiZRFf5Oqxk+UGm1sbMNTsg+Cm93EEGNQ==";
    private static final String PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmjXlrWfxmJqUi8aM+g/aokg976ufURAZhmT0HSKXmosujqso8drHd1HGztSrGlwjim4y7c0JgV/XL2LDRiapE6rgkd/yI0mMDS6sD6BwZGxwFk5lCuXFOh+BWitGqOmmGx15L0WjaxaN++0ZysZ0f/vkCtFntcn0H2b4CidRy8aNcKM8gmhiA0k5x+ghcrMFPjnGf4jrvEFb7CD3dFLodMmNMnFJK8KJRFqA38ZFOQkTqV5cwy5Of3KAAKq1l2l7VfmkGGpZLwvE/SOZDOdJ8utSMDsCNrlSo1VT3f7joSAX7JdIxx+IA4GNbzN2h7IIGXGAdKc1wVGKsNGCehJoHwIDAQAB";

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
        // 2048位
        KeyPair pair = SecureUtil.generateKeyPair("RSA", 2048);
        PrivateKey aPrivate = pair.getPrivate();
        PublicKey aPublic = pair.getPublic();
        String privateKeyString = Base64.encode(aPrivate.getEncoded());
        String publicKeyString = Base64.encode(aPublic.getEncoded());
        System.out.println("privateKey: " + privateKeyString);
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
    void tokenRS256() throws Exception {
        byte[] decode = Decoders.BASE64.decode(PrivateKey);
        byte[] publicKeyDecode = Decoders.BASE64.decode(PublicKey);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decode);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey key1 = factory.generatePrivate(spec);

        X509EncodedKeySpec spec1 = new X509EncodedKeySpec(publicKeyDecode);
        KeyFactory factory1 = KeyFactory.getInstance("RSA");
        PublicKey key2 = factory1.generatePublic(spec1);

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
                .signWith(key1, SignatureAlgorithm.RS256).compact();
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
