package com.aircraft.codelab.auth.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 2021-10-13
 *
 * @author tao.zhang
 * @since 1.0
 */
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private CertInfo certInfo;
    private Claims claims;
    /**
     * The cert info.
     */
    @Data
    public static class CertInfo {
        /**
         * certificate alias,required
         */
        private String alias;
        /**
         * certificate password,required
         */
        private String keyPassword;
        /**
         * certificate path,required
         */
        private String certLocation;
    }

    /**
     * The jwt claims.
     */
    @Data
    public static class Claims {
        /**
         * jwt issuer HTTPS
         */
        private String issuer;
        /**
         * jwt subject
         */
        private String subject;
        /**
         * jwt expired instant
         */
        private Integer expiresAt;
    }
}
