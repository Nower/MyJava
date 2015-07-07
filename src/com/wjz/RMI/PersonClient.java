package com.wjz.RMI;

/**
 * Created by wujiazhi on 15/7/6.
 */
public class PersonClient {
    public static void main(String[] args) {
        try {
            Person person = new PersonStub();
            int age = person.getAge();
            String name = person.getName();
            System.out.println(name + " is " + age + " years old");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
