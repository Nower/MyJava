package com.wjz.JMX;

/**
 * Created by wujiazhi on 15/6/23.
 */
public interface HelloWorldMBean {
    public String getName();
    public void setName(String name);
    public void printHello();
    public void printHello(String whoName);
}

