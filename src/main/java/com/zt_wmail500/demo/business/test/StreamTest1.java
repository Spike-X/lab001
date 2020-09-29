package com.zt_wmail500.demo.business.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: demo
 * @description: Stream测试
 * @author: tao.zhang
 * @create: 2020-08-08 18:59
 **/
public class StreamTest1 {

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("aa", "bb", "cc","bbc","acd","btv");

        List<String> b = strings.stream()
                .parallel()
                .filter(s -> s.startsWith("b"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        b.forEach(System.out::println);
        System.out.println("-----------");
        b.removeIf(e -> e.contains("C"));
        b.forEach(e -> {
            if (e.endsWith("B")) {
                System.out.println(e);
            }
        });
    }
}
