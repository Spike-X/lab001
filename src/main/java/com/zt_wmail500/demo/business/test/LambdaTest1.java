package com.zt_wmail500.demo.business.test;

/**
 * @program: demo
 * @description: lambda测试
 * @author: tao.zhang
 * @create: 2020-06-21 22:04
 **/
public class LambdaTest1 {

    public static void main(String[] args) {

        //无参无返回
        LambdaInterface.NoReturnNoParam noReturnNoParam = () -> {
            System.out.println("NoReturnNoParam");
        };
        noReturnNoParam.method();

        //一个参数无返回
        LambdaInterface.NoReturnOneParam noReturnOneParam = (int a) -> {
            System.out.println("NoReturnOneParam param:" + a);
        };
        noReturnOneParam.method(6);

        //多个参数无返回
        LambdaInterface.NoReturnMultiParam noReturnMultiParam = (int a, int b) -> {
            System.out.println("NoReturnMultiParam param:" + "{" + a +"," + + b +"}");
        };
        noReturnMultiParam.method(6, 8);

        //无参有返回值
        LambdaInterface.ReturnNoParam returnNoParam = () -> {
            System.out.print("ReturnNoParam");
            return 1;
        };
        int res = returnNoParam.method();
        System.out.println("return:" + res);

        //一个参数有返回值
        LambdaInterface.ReturnOneParam returnOneParam = (int a) -> {
            System.out.println("ReturnOneParam param:" + a);
            return 1;
        };
        int res2 = returnOneParam.method(6);
        System.out.println("return:" + res2);

        //多个参数有返回值
        LambdaInterface.ReturnMultiParam returnMultiParam = (int a, int b) -> {
            System.out.println("ReturnMultiParam param:" + "{" + a + "," + b +"}");
            return 1;
        };
        int res3 = returnMultiParam.method(6, 8);
        System.out.println("return:" + res3);

        //匿名内部类测试
        System.out.println("-------------");
        Outer o = new Outer();
        o.method();
    }

}

