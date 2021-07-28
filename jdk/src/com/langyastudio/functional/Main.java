package com.langyastudio.functional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Main
{
    public static void main(String[] args)
    {
        //--------------------------------------------------------------------------
        // FunctionalInterface
        // Predicate、Function、Consumer、Supplier、Operator
        //--------------------------------------------------------------------------
        //Function - 有输入有输出
        Function<String, Integer> lenFunction = (str) -> {return str.length();};
        System.out.println("apple length:" + lenFunction.apply("apple"));

        //组合Function
        Function<Integer, Integer> multiFunction = x -> x*x;
        //第一个函数的返回值作为第二个函数的输入
        Function<String, Integer> andFunction = lenFunction.andThen(multiFunction);
        System.out.println("andFunction:" + andFunction.apply("apple"));

        //自定义Function
        TriFunction<String, String, String, Integer> allLenFunction =
                (str1, str2, str3) -> str1.length() + str2.length() + str3.length();
        System.out.println("all length:" + allLenFunction.apply("Apple", "Orange", "Banana"));

        //--------------------------------------------------------------------------
        // Stream
        //--------------------------------------------------------------------------
        //基于数组创建
        Arrays.stream(new String[]{"Apple", "Orange", "Banana"})
                .forEach(System.out::println);

        //基于Collect创建
        List.of("Apple", "Orange", "Banana")
                .stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .filter((str) -> str.length() > 5)
                .forEach(System.out::println);

        //--------------------------------------------------------------------------
        // Optional
        //--------------------------------------------------------------------------
        String str = null;
        Optional<String> stringOptional = Optional.ofNullable(str);
        if(stringOptional.isPresent())
        {
            //此时下面的代码不会执行
            System.out.println(stringOptional);
        }
    }
}
