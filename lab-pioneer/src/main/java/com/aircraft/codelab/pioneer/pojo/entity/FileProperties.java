package com.aircraft.codelab.pioneer.pojo.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 2021-12-03
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String uploadDir;
}
