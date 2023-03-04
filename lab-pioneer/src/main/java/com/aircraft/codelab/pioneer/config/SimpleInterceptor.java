package com.aircraft.codelab.pioneer.config;

import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.interceptor.Interceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * 2022-12-04
 * Forest请求的拦截器
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
public class SimpleInterceptor<T> implements Interceptor<T> {
    /**
     * 该方法在请求发送失败时被调用
     */
    @Override
    public void onError(ForestRuntimeException ex, ForestRequest req, ForestResponse res) {
        log.info("invoke Simple onError");
        String path = req.getPath();
        Object query = req.getQuery("");
        // 执行发送请求失败后处理的代码
        int status = res.getStatusCode(); // 获取请求响应状态码
        String content = res.getContent(); // 获取请求的响应内容
        Object result = res.getResult();// 获取方法返回类型对应的返回数据结果
    }
}
