package com.aircraft.codelab.pioneer.util;

import com.aircraft.codelab.pioneer.Lab001Application;
import com.aircraft.codelab.pioneer.pojo.vo.SysMenuCreatVo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * 2022-05-15
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@SpringBootTest(classes = {Lab001Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NoRepeatSubmitTest {
    @Resource
    private MockMvc mockMvc;

    @Test
    @DisplayName("防重复提交")
    void test() throws Exception {
        SysMenuCreatVo sysMenuCreatVo = new SysMenuCreatVo();
        sysMenuCreatVo.setMenuName("测试菜单");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/hello/submit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JSON.toJSONString(sysMenuCreatVo)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                // $: 返回结果
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("请求成功"))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(Charset.defaultCharset());
        log.debug("返回结果 : {}", contentAsString);
    }
}
