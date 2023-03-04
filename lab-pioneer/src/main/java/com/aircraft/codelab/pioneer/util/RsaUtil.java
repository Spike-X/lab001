package com.aircraft.codelab.pioneer.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 2021-12-30
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class RsaUtil {
    private RsaUtil() {
    }

    @SneakyThrows
    public static PublicKey getPublicKey(String Base64EncodeKey) {
        byte[] decode = Base64.getDecoder().decode(Base64EncodeKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decode);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    @SneakyThrows
    public static PrivateKey getPrivateKey(String Base64EncodeKey) {
        byte[] decode = Base64.getDecoder().decode(Base64EncodeKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decode);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    // 生成rsa秘钥对
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // RSA加密算法
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // RSA密钥长度2048
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成公钥
        PublicKey rsaPublicKey = keyPair.getPublic();
        // 生成私钥
        PrivateKey rsaPrivateKey = keyPair.getPrivate();
        // Base64编码
        String publicKey = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
        log.debug("publicKey: {}", publicKey);
        log.debug("privateKey: {}", privateKey);
    }
}
