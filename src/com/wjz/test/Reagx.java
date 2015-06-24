package com.wjz.test;

/**
 * Created by wujiazhi on 15/5/26.
 */
public class Reagx {
    public static void main(String []args){
        String mackey = "kafkldjflkajfkd'asjfkd'fa    fakdfa'lfjkd'as fdas'kj'fdasjk'fd";
        String regex = "\\s+";
        String[] strs = mackey.split(regex);
        String encodekey = strs[1];
        String checkvalue = strs[2];

        System.out.println(encodekey + " " + checkvalue);

    }
}
