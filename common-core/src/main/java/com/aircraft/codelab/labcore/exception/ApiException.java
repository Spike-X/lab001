package com.aircraft.codelab.labcore.exception;

import com.aircraft.codelab.labcore.service.IReturnCode;

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
