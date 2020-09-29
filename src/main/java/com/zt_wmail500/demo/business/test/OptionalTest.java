package com.zt_wmail500.demo.business.test;

import java.util.Optional;

/**
 * @program: demo
 * @description: optional测试
 * @author: tao.zhang
 * @create: 2020-08-16 23:12
 **/
public class OptionalTest {

    public static void main(String[] args) {
        String strA = "abcd", strB = null;
        print(strA);
        int len1 = getLength(strA);
        System.out.println(len1);
        print("");
        int len2 = getLength("");
        System.out.println(len2);
        print(strB);
        int len3 = getLength(strB);
        System.out.println(len3);
    }

    public static void print(String text) {
        // Java 8
        Optional.ofNullable(text).ifPresent(System.out::println);
        // Pre-Java 8
//        if (text != null) {
//            System.out.println(text);
//        }
    }

    public static int getLength(String text) {
        // Java 8
        return Optional.ofNullable(text).map(String::length).orElse(-1);
        // Pre-Java 8
//        return if (text != null) ? text.length() : -1;
    }
}
