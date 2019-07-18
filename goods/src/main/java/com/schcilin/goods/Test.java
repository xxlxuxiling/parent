package com.schcilin.goods;

/**
 * @Author: schcilin
 * @Date: 2019/7/8 14:12
 * @Content:
 */


public class Test {

    public static void main(String[] args) {
        ClassLoader classLoader = Test.class.getClassLoader();
        while (classLoader != null) {
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        }
    }
}
