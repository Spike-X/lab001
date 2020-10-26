package com.zt_wmail500.demo.business.test;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @program: demo
 * @description: test2
 * @author: tao.zhang
 * @create: 2020-08-09 22:43
 **/
public class StreamTest2 {

    public static void main(String[] args) {

//        String[] s = new String[]{"apple","banana","orange","watermelon","grape"};
//        Stream.of(s)
//                .map(String::length) //转成单词的长度 int
//                .forEach(System.out::println); //输出

//        String s =Stream.of("a-b-c-d","e-f-i-g-h")
//                .flatMap(e->Stream.of(e.split("-")))
//                .collect(Collectors.joining());
//        System.out.println(s);

//        Stream.of("apple", "banana", "orange", "watermelon", "grape")
//                .collect(Collectors.toSet()) //set 容器
//                .forEach(System.out::println);

        Optional<String> stringOptional = Stream.of("apple", "banana", "orange", "watermelon", "grape")
                .parallel()
                .findAny(); //在并行流下每次返回的结果可能一样也可能不一样
        stringOptional.ifPresent(System.out::println);

//        int sum = Stream.of(0,9,8,4,5,6,-1)
//                .reduce(0, Integer::sum);
//        System.out.println(sum);


    }


}
