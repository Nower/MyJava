package com.wjz.JMX;

/**
 * Created by wujiazhi on 15/6/23.
 */
public class HelloWorld implements HelloWorldMBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printHello() {
        System.out.println("Hello World, " + name);
    }

    public void printHello(String whoName) {
        System.out.println("Hello , " + whoName);
    }

}