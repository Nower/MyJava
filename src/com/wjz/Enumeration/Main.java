package com.wjz.Enumeration;

/**
 * Created by wujiazhi on 15/6/24.
 * 1、反编译学习枚举
 * 2、所有的枚举都继承自java.lang.Enum类。由于Java 不支持多继承，所以枚举对象不能再继承其他类。
 * 3. 枚举中的属性必须放在最前面，一般使用大写字母表示
 * 4. 枚举中可以和java类一样定义方法
 * 5. 枚举中的构造方法必须是私有的
 */

enum Color {
    RED, GREEN, BLANK, YELLOW
}

enum ColorI{
    RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLOW("黄色", 4);


    private String name;
    private int index;

    private ColorI(String name, int index){
        this.name = name;
        this.index = index;
    }

    @Override
    public String toString() {
        return this.index + "_" + this.name;
    }

    public static String getName(int index){
        for(ColorI ci : ColorI.values()){
            if(ci.index == index){
                return ci.name;
            }
        }
        return "Wrong index!";
    }

    public String getName(){
        return name;
    }


}

interface Behavior{
    void print();
    String getInfo();
}

enum ColorII implements Behavior{
    RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLOW("黄色", 4);


    private String name;
    private int index;

    private ColorII(String name, int index){
        this.name = name;
        this.index = index;
    }

    @Override
    public String toString() {
        return this.index + "_" + this.name;
    }

    @Override
    public void print() {
        System.out.println(toString());
    }

    @Override
    public String getInfo() {
        return toString();
    }
}

public class Main {
    public static void main(String[]args){
        Color [] colors = {Color.RED, Color.GREEN, Color.BLANK, Color.YELLOW};
        for(Color color : colors){
            testSwitch(color);
        }
    }


    public static void testSwitch(Color color){
        switch (color){
            case RED:
                System.out.println("Red color!");
                break;
            case GREEN:
                System.out.println("Green? Ah...");
                break;
        }

    }
}
