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

package com.aircraft.codelab.core.exception;

import com.aircraft.codelab.core.service.IReturnCode;

/**
 * 2020-11-03
 * 自定义异常
 *
 * @author tao.zhang
 * @since 1.0
 */
public class ApiException extends RuntimeException {
    private IReturnCode returnCode;

    public ApiException(IReturnCode iReturnCode) {
        super(iReturnCode.getMessage());
        this.returnCode = iReturnCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IReturnCode getReturnCode() {
        return returnCode;
    }
}
