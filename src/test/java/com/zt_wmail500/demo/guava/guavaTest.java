package com.zt_wmail500.demo.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @program: demo
 * @description: guava测试
 * @author: tao.zhang
 * @create: 2020-10-03 16:45
 **/
@DisplayName("guava测试")
public class guavaTest {

    @Test
    public void joinerListTest() {
        List<String> lists = Lists.newArrayList("a", "b", "g", "8", "9");
        String result = Joiner.on(",").join(lists);
        System.out.println(result);
    }

}
