package com.aircraft.codelab.pioneer.pojo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

/**
 * 2022-12-17
 * 工作日
 *
 * @author tao.zhang
 * @since 1.0
 */
@Data
public class HolidayDto {
    private String code;
    private Map<String, Vacation> holiday;

    @Data
    public static class Vacation {
        /**
         * 节假日的日期
         */
        private LocalDate date;
        /**
         * 节假日的中文名
         */
        private String name;
        /**
         * true表示是节假日，false表示是调休
         */
        private Boolean holiday;

        /**
         * 薪资倍数
         */
        private String wage;
        /**
         * true表示放完假后调休，false表示先调休再放假
         */
        private Boolean after;
        /**
         * 表示调休的节假日
         */
        private String target;
    }
}
