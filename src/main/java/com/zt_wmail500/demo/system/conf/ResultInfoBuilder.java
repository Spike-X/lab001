package com.zt_wmail500.demo.system.conf;

/**
 * @program: demo
 * @description: 将结果转换为封装后的对象
 * @author: tao.zhang
 * @create: 2020-10-27 21:08
 **/
public class ResultInfoBuilder {

    public static <T> ResultInfo<T> success() {
        return new ResultInfo<T>(ResultCode.SUCCESS);
    }

    public static <T> ResultInfo<T> success(ResultCode resultCode) {
        return new ResultInfo<T>(resultCode);
    }

    public static <T> ResultInfo<T> success(ResultCode resultCode, T data) {
        return new ResultInfo<T>(resultCode, data);
    }
}
