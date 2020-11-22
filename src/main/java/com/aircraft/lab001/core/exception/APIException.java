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

package com.aircraft.lab001.core.exception;

import com.aircraft.lab001.core.service.IReturnCode;

/**
 * @description: 自定义异常
 * @author: spikeX
 * @create: 2020-11-03
 **/
public class APIException extends RuntimeException {
    private IReturnCode returnCode;

    public APIException(IReturnCode iReturnCode) {
        super(iReturnCode.getMessage());
        this.returnCode = iReturnCode;
    }

    public APIException(String message) {
        super(message);
    }

    public APIException(Throwable cause) {
        super(cause);
    }

    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    public IReturnCode getReturnCode() {
        return returnCode;
    }
}
