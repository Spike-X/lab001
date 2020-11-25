/*
 * Copyright (c) 2020, Tao Zhang (zt_wmail500@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aircraft.lab001.core.util;

import com.aircraft.lab001.core.exception.ApiException;
import com.google.common.base.Strings;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 2020-11-08
 * 自定义断言类
 *
 * @author tao.zhang
 * @since 1.0
 */
public final class Assert {
    private Assert() {
    }

    public static void isTrue(boolean expression, String message, Object... params) {
        if (!expression) {
            throw new ApiException(Strings.lenientFormat(message, params));
        }
    }

    public static void isFalse(boolean expression, String message, Object... params) {
        isTrue(!expression, message, params);
    }

    public static void isNull(Object object, String message, Object... params) {
        isTrue(object == null, message, params);
    }

    public static void notNull(Object object, String message, Object... params) {
        isTrue(object != null, message, params);
    }

    public static void notEmpty(String value, String message, Object... params) {
        isTrue(StringUtils.isNotBlank(value), message, params);
    }

    public static void notEmpty(Object[] array, String message, Object... params) {
        isTrue(ArrayUtils.isNotEmpty(array), message, params);
    }

    public static void notEmpty(Map<?, ?> map, String message, Object... params) {
        isTrue(MapUtils.isNotEmpty(map), message, params);
    }

    public static void notEmpty(Collection<?> collection, String message, Object... params) {
        isTrue(CollectionUtils.isNotEmpty(collection), message, params);
    }
}
