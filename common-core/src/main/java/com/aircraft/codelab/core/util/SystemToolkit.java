package com.aircraft.codelab.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 2021-08-18
 * 系统工具包
 *
 * @author tao.zhang
 * @since 1.0
 */
public class SystemToolkit {
    // JsonUtils
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
    // JacksonConfig
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));

            builder.locale(Locale.CHINA)
                    .timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
                    .modules(javaTimeModule)
                    .serializerByType(Long.class, ToStringSerializer.instance)
                    .serializerByType(Long.TYPE, ToStringSerializer.instance)
                    .serializerByType(BigInteger.class, ToStringSerializer.instance);
        };
    }
}
