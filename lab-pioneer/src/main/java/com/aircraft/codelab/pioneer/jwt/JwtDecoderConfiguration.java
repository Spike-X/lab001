package com.aircraft.codelab.pioneer.jwt;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

import javax.annotation.Resource;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Collection;

/**
 * 2021-12-29
 *
 * @author tao.zhang
 * @since 1.0
 */
//@EnableConfigurationProperties(JwtProperties.class)
//@Configuration(proxyBeanMethods = false)
public class JwtDecoderConfiguration {
    @Resource
    private JwtProperties jwtProperties;

    /**
     * 校验jwt发行者 issuer 是否合法
     *
     * @return the jwt issuer validator
     */
    @Bean
    JwtIssuerValidator jwtIssuerValidator() {
        return new JwtIssuerValidator(jwtProperties.getClaims().getIssuer());
    }

    /**
     * 校验jwt是否过期
     *
     * @return the jwt timestamp validator
     */
    @Bean
    JwtTimestampValidator jwtTimestampValidator() {
        return new JwtTimestampValidator(Duration.ofSeconds((long) jwtProperties.getClaims().getExpiresAt()));
    }

    /**
     * jwt token 委托校验器，集中校验的策略{@link OAuth2TokenValidator}
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
     * 基于Nimbus的jwt解码器，并增加了一些自定义校验策略
     *
     * @param validator the validator
     * @return the jwt decoder
     */
    @SneakyThrows
    @Bean
    public JwtDecoder jwtDecoder(@Qualifier("delegatingTokenValidator") DelegatingOAuth2TokenValidator<Jwt> validator) {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        // 读取cer公钥证书来配置解码器
        ClassPathResource resource = new ClassPathResource(jwtProperties.getCertInfo().getPublicKeyLocation());
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(resource.getInputStream());
        RSAKey rsaKey = RSAKey.parse(certificate);
        NimbusJwtDecoder nimbusJwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
        nimbusJwtDecoder.setJwtValidator(validator);
        return nimbusJwtDecoder;
    }
}
