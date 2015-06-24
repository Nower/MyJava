package com.wjz.Enumeration;

/**
 * Created by wujiazhi on 15/6/24.
 * 枚举单例的好处
 1.线程安全，不会因为多线程创建多个实例
 2.不会因为序列化而产生新实例，反序列化会调用readResolve（）创建新的实例，
   防止方法是在readResolve函数中返回INSTANCE；
 3.防止反射攻击。可以通过私有构造函数来实例化单例类
 以上问题不用枚举类都可以用方法解决，但是很麻烦，而用枚举就会特别简单

 枚举单例的缺点：无法实现懒加载
 */
public enum Singleton {
    INSTANCE;

    private Singleton(){
    }

    public static void test(){
        System.out.println("Test!");
    }
}
