package com.aircraft.codelab.pioneer.service;

import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.pioneer.config.SimpleInterceptor;
import com.aircraft.codelab.pioneer.pojo.entity.UserDO;
import com.dtflys.forest.annotation.*;
import com.dtflys.forest.backend.ContentType;

import java.util.Map;

/**
 * 2022-12-03
 * 官网 https://forest.dtflyx.com/
 * 声明式接口
 *
 * @author tao.zhang
 * @since 1.0
 */

// 使用 @BaseRequest 为配置接口层级请求信息的注解
// 其属性会成为该接口下所有请求的默认属性但可以被方法上定义的属性所覆盖
@BaseRequest(
        baseURL = "http://localhost:8081",     // 默认域名
        sslProtocol = "TLSv1.2", interceptor = SimpleInterceptor.class                    // 默认单向SSL协议
)
// 若全局变量中已定义 baseUrl 和 accept
// 便会将全局变量中的值绑定到 @BaseRequest 的属性中
/*@BaseRequest(
        baseURL = "${baseUrl}",     // 默认域名
        headers = {
                "Accept:${accept}"      // 默认请求头
        }
)*/
public interface ForestClient {
    @Get(url = "/test/tomcat/actuator", dataType = "json")
    CommonResult<Map<String, Object>> helloForest();

    /**
     * 使用 @Query 注解，可以直接将该注解修饰的参数动态绑定到请求url中
     * 注解的 value 值即代表它在url的Query部分的参数名
     */
    @Get(url = "/test")
    String send1(@Query("a") String a, @Query("b") String b);

    /**
     * 使用 @Query 注解，可以修饰 Map 类型的参数
     * 很自然的，Map 的 Key 将作为 URL 的参数名， Value 将作为 URL 的参数值
     * 这时候 @Query 注解不定义名称
     */
    @Get("/test")
    String send2(@Query Map<String, Object> map);

    /**
     * 使用 @Query 注解也可以修饰自定义类型的对象参数
     * 依据对象类的 Getter 和 Setter 的规则取出属性
     * 其属性名为 URL 参数名，属性值为 URL 参数值
     * 这时候 @Query 注解不定义名称
     */
    @Get("/test")
    String send3(@Query UserDO user);

    /**
     * 使用 @Header 注解可以修饰 Map 类型的参数
     * Map 的 Key 指为请求头的名称，Value 为请求头的值
     * 通过此方式，可以将 Map 中所有的键值对批量地绑定到请求头中
     */
    @Post("/hello/user?username=foo")
    void headHelloUser(@Header Map<String, Object> headerMap);

    /**
     * contentType属性设置为 application/x-www-form-urlencoded 即为表单格式，
     * 当然不设置的时候默认值也为 application/x-www-form-urlencoded， 也同样是表单格式。
     * 在 @Body 注解的 value 属性中设置的名称为表单项的 key 名，
     * 而注解所修饰的参数值即为表单项的值，它可以为任何类型，不过最终都会转换为字符串进行传输。
     */
    @Post(
            url = "/user",
            contentType = ContentType.APPLICATION_X_WWW_FORM_URLENCODED
    )
    String sendPost(@Body("key1") String value1, @Body("key2") Integer value2, @Body("key3") Long value3);

    /**
     * contentType 属性不设置默认为 application/x-www-form-urlencoded
     * 要以对象作为表达传输项时，其 @Body 注解的 value 名称不能设置
     */
    @Post("/hello/user")
    String send(@Body UserDO user);

    /**
     * 被@JSONBody注解修饰的参数会根据其类型被自定解析为JSON字符串
     * 使用@JSONBody注解时可以省略 contentType = "application/json"属性设置
     */
    @Post("/hello/user")
    String helloUser(@JSONBody UserDO user);
}
