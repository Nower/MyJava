package com.wjz.RMI;

/**
 * Created by wujiazhi on 15/7/6.
 */
public class PersonServer implements Person {
    private int age;
    private String name;

    public PersonServer(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
