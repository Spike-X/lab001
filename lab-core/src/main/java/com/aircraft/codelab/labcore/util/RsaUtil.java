package com.aircraft.codelab.labcore.util;

import cn.hutool.core.codec.Base64;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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
        byte[] decode = Base64.decode(Base64EncodeKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decode);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    @SneakyThrows
    public static PrivateKey getPrivateKey(String Base64EncodeKey) {
        byte[] decode = Base64.decode(Base64EncodeKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decode);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    public static void main(String[] args) throws JOSEException {
        // RSA signatures require a public and private RSA key pair, the public key
        // must be made known to the JWS recipient in order to verify the signatures
        RSAKey rsaJWK = new RSAKeyGenerator(2048).generate();
        RSAPublicKey rsaPublicKey = rsaJWK.toRSAPublicKey();
        RSAPrivateKey rsaPrivateKey = rsaJWK.toRSAPrivateKey();
        // Base64编码
        String publicKey = Base64.encode(rsaPublicKey.getEncoded());
        String privateKey = Base64.encode(rsaPrivateKey.getEncoded());
        log.debug("publicKey: {}", publicKey);
        log.debug("privateKey: {}", privateKey);
    }
}
