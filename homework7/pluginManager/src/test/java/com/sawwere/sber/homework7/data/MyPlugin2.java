package org.example;

import com.sawwere.sber.homework7.Plugin;

public class MyPlugin2 implements Plugin {
    @Override
    public void doUsefully() {
        System.out.println("Плагин №2 делает что-то полезное! =)");
    }
}