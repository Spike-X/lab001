package com.aircraft.codelab.auth.config;

import com.aircraft.codelab.auth.jwt.JwtProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.Collection;

/**
 * 2021-10-13
 *
 * @author tao.zhang
 * @since 1.0
 */
@EnableConfigurationProperties(JwtProperties.class)
@Configuration(proxyBeanMethods = false)
public class JwtConfiguration {
    /**
     * 加载 JKS 证书库
     */
    private static final KeyStore JKS_STORE;
    @Resource
    private JwtProperties jwtProperties;

    static {
        try {
            JKS_STORE = KeyStore.getInstance("jks");
        } catch (KeyStoreException e) {
            throw new RuntimeException("can not obtain jks keystore instance");
        }
    }


    /**
     * 获取JWK (JSON Web Key)  包含了JOSE(可以认为是JWT的超集) 加密解密 签名验签的Key
     *
     * @return the jwk set
     * @throws KeyStoreException        the key store exception
     * @throws IOException              the io exception
     * @throws JOSEException            the jose exception
     * @throws CertificateException     the certificate exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    @Bean
    @ConditionalOnMissingBean
    public JWKSource<SecurityContext> jwkSource() throws KeyStoreException, IOException, JOSEException, CertificateException, NoSuchAlgorithmException {

        JwtProperties.CertInfo certInfo = jwtProperties.getCertInfo();
        ClassPathResource classPathResource = new ClassPathResource(certInfo.getCertLocation());
        char[] pin = certInfo.getKeyPassword().toCharArray();
        JKS_STORE.load(classPathResource.getInputStream(), pin);

        RSAKey rsaKey = RSAKey.load(JKS_STORE, certInfo.getAlias(), pin);

        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }


    /**
     * 用JWK来生成JWT的工具，底层使用了Nimbus库，这个库是Spring Security OAuth2 Client 默认引用的库
     *
     * @param jwkSource the jwk source
     * @return the jwt encoder
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwsEncoder(jwkSource);
    }

    /**
     * 这个不陌生，去看Spring Security 干货系列
     *
     * @return the jwt token storage
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtTokenStorage jwtTokenStorage() {
        return new SpringCacheJwtTokenStorage();
    }

    /**
     * 🥈🥈🥈：这个顾名思义，JWT生成器，用来生成accessToken和refreshToken对
     * 是生成或者刷新JWT的唯一出口。
     *
     * @param jwtTokenStorage the jwt token storage
     * @param jwtEncoder      the jwt encoder
     * @return the jwt token generator
     */
    @Bean
    public JwtTokenGenerator jwtTokenGenerator(JwtTokenStorage jwtTokenStorage, JwtEncoder jwtEncoder) {
        return new JwtTokenGenerator(jwtProperties.getClaims(), jwtTokenStorage, jwtEncoder);
    }


    /**
     * 校验JWT issuer
     *
     * @return the jwt issuer validator
     * @see DelegatingOAuth2TokenValidator
     */
    @Bean
    JwtIssuerValidator jwtIssuerValidator() {
        return new JwtIssuerValidator(jwtProperties.getClaims().getIssuer());
    }

    /**
     * 校验JWT是否过期
     *
     * @return the jwt timestamp validator
     * @see DelegatingOAuth2TokenValidator
     */
    @Bean
    JwtTimestampValidator jwtTimestampValidator() {
        return new JwtTimestampValidator(Duration.ofSeconds(jwtProperties.getClaims().getExpiresAt()));
    }

    /**
     * JWT 委托校验器，用来执行多个JWT校验策略，如果有其它校验需要可自行实现{@link OAuth2TokenValidator}
     *
     * @param tokenValidators the token validators
     * @return the delegating o auth 2 token validator
     */
    @Primary
    @Bean("delegatingTokenValidator")
    public DelegatingOAuth2TokenValidator<Jwt> delegatingTokenValidator(Collection<OAuth2TokenValidator<Jwt>> tokenValidators) {
        return new DelegatingOAuth2TokenValidator<>(tokenValidators);
    }


    /**
     * Jwt decoder jwt decoder.
     *
     * @param jwkSource the jwk source
     * @return the jwt decoder
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource, @Qualifier("delegatingTokenValidator") DelegatingOAuth2TokenValidator<Jwt> validator) {
        JWSVerificationKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSource);

        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        jwtProcessor.setJWSKeySelector(keySelector);

        NimbusJwtDecoder nimbusJwtDecoder = new NimbusJwtDecoder(jwtProcessor);
        nimbusJwtDecoder.setJwtValidator(validator);

        return nimbusJwtDecoder;
    }
}
