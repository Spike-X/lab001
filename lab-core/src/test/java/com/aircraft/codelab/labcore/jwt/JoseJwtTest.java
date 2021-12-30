package com.aircraft.codelab.labcore.jwt;

import cn.hutool.core.codec.Base64;
import com.aircraft.codelab.labcore.util.RsaUtil;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * 2021-12-30
 *
 * @author tao.zhang
 * @since 1.0
 */
public class JoseJwtTest {
    @SneakyThrows
    @Test
    void jose() {
        // RSA signatures require a public and private RSA key pair, the public key
        // must be made known to the JWS recipient in order to verify the signatures
        RSAKey rsaJWK = new RSAKeyGenerator(2048).generate();
        RSAPublicKey rsaPublicKey = rsaJWK.toRSAPublicKey();
        RSAPrivateKey rsaPrivateKey = rsaJWK.toRSAPrivateKey();
        String publicKey = Base64.encode(rsaPublicKey.getEncoded());
        String privateKey = Base64.encode(rsaPrivateKey.getEncoded());

        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner(RsaUtil.getPrivateKey(privateKey));

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("alice")
                .issuer("https://c2id.com")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).build(), claimsSet);

        // Compute the RSA signature
        signedJWT.sign(signer);

        // To serialize to compact form, produces something like
        // eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L
        // mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd
        // maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7
        // -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A
        String signature = signedJWT.serialize();
        System.out.println(signature);

        // On the consumer side, parse the JWS and verify its RSA signature
        SignedJWT signed = SignedJWT.parse(signature);

        JWSVerifier jwsVerifier = new RSASSAVerifier((RSAPublicKey) RsaUtil.getPublicKey(publicKey));
        Assertions.assertTrue(signed.verify(jwsVerifier));
        // Retrieve / verify the JWT claims according to the app requirements
        Assertions.assertTrue(new Date().before(signed.getJWTClaimsSet().getExpirationTime()));
        Assertions.assertEquals("alice", signed.getJWTClaimsSet().getSubject());
        Assertions.assertEquals("https://c2id.com", signed.getJWTClaimsSet().getIssuer());
    }
}
