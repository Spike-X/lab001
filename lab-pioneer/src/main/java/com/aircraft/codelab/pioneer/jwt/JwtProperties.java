package com.aircraft.codelab.pioneer.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 2021-12-29
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private CertInfo certInfo;
    private Claims claims;

    @Data
    public static class Claims {
        private String issuer;
        private Integer expiresAt;
    }

    @Data
    public static class CertInfo {
        private String publicKeyLocation;
    }
}
