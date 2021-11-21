package com.aircraft.codelab.core.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 2021-11-22
 *
 * @author tao.zhang
 * @since 1.0
 */
public class SnowflakeUtil {
    private SnowflakeUtil() {
    }

    private static class SingletonHolder {
        //参数1为终端ID
        //参数2为数据中心ID
        private static final Snowflake snowflake = IdUtil.getSnowflake(1, 1);
    }

    public static Snowflake getInstance() {
        return SingletonHolder.snowflake;
    }
}
