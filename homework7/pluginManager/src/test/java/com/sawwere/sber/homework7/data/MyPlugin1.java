package org.example;

import com.sawwere.sber.homework7.Plugin;

public class MyPlugin1 implements Plugin {
    @Override
    public void doUsefully() {
        System.out.println("Плагин №1 делает что-то полезное! =)");
    }
}