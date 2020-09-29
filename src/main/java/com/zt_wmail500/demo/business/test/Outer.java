package com.zt_wmail500.demo.business.test;

import com.zt_wmail500.demo.business.service.Inter;

/**
 * @program: demo
 * @description: 外部类
 * @author: tao.zhang
 * @create: 2020-06-21 23:49
 **/
public class Outer {

    public void method(){
        // lambda
        Inter inter1 = () -> System.out.println("lambda匿名内部类");
        inter1.show();

        // 普通匿名内部类
        Inter inter2 = new Inter() {
            public void show() {
                System.out.println("普通匿名内部类");
            }
            public void print() {
                System.out.println("aaa");
            }
        };
        inter2.print();
    }

    public static void main(String[] args) {
        LambdaInterface.ReturnOneParam lambda1 = a -> doubleNum(a);
        System.out.println(lambda1.method(1));

        //lambda2 引用了已经实现的 doubleNum 方法
        LambdaInterface.ReturnOneParam lambda2 = Outer::doubleNum;
        System.out.println(lambda2.method(2));

        Outer exe = new Outer();
        //lambda3 引用了已经实现的 addTwo 方法
        LambdaInterface.ReturnOneParam lambda3 = exe::addTwo;
        System.out.println(lambda3.method(3));
    }

    /**
     * 要求
     * 1.参数数量和类型要与接口中定义的一致
     * 2.返回值类型要与接口中定义的一致
     */
    public static int doubleNum(int a) {
        return a * 2;
    }

    public int addTwo(int a) {
        return a + 2;
    }

}
