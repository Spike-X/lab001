package com.zt_wmail500.demo.system.conf;

/**
 * @program: demo
 * @description: 统一返回对象实体
 * @author: tao.zhang
 * @create: 2020-10-26 21:42
 **/
public class ResultInfo<T> {
    /**
     * 状态码，比如1000代表响应成功
     */
    private final int code;

    /**
     * 响应信息，用来说明响应情况
     */
    private final String message;

    /**
     * 响应的具体数据
     */
    private T data;

    public ResultInfo(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public ResultInfo(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
