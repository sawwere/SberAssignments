package com.sawwere.sber.homework25;


import java.util.ArrayList;
import java.util.List;

public class GCTask {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Loading...");
        Thread.sleep(6 * 1000L);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 200; i++){
            list.add(new String(new char[1024*1024])); // 1 MB
        }
        System.out.println(list.size() + " objects are loaded into memory.");
        Thread.sleep(5 * 1000L);
    }
}