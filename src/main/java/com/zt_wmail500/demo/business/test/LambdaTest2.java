package com.zt_wmail500.demo.business.test;

/**
 * @program: demo
 * @description: 简化写法
 * @author: tao.zhang
 * @create: 2020-06-21 22:11
 **/
public class LambdaTest2 {
    public static void main(String[] args) {

        //1.简化参数类型，可以不写参数类型，但是必须所有参数都不写
        LambdaInterface.NoReturnMultiParam lambda1 = (a, b) -> {
            System.out.println("简化参数类型");
        };
        lambda1.method(1, 2);

        //2.简化参数小括号，如果只有一个参数则可以省略参数小括号
        LambdaInterface.NoReturnOneParam lambda2 = a -> {
            System.out.println("简化参数小括号");
        };
        lambda2.method(1);

        //3.简化方法体大括号，如果方法体只有一条语句，则可以省略方法体大括号
        LambdaInterface.NoReturnNoParam lambda3 = () -> System.out.println("简化方法体大括号");
        lambda3.method();

        //4.如果方法体只有一条语句，并且是 return 语句，则可以省略方法体大括号
        LambdaInterface.ReturnOneParam lambda4 = a -> a + 3;
        System.out.println(lambda4.method(5));

        LambdaInterface.ReturnMultiParam lambda5 = Integer::sum;
        System.out.println(lambda5.method(1, 1));
    }
}
