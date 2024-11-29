package com.sawwere.sber.homework4.terminal;

public class TerminalConsoleDisplay implements TerminalDisplay {
    @Override
    public void display(String message) {
        System.out.println(message);
    }
}
