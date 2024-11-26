package com.sawwere.sber.homework4;

import com.sawwere.sber.homework4.terminal.*;

public class Main {
    public static void main(String[] args) {
        String correctPin = "2214";

        Terminal terminal = new TerminalImpl(
                new TerminalServerImpl(correctPin),
                new TerminalConsoleDisplay(),
                new PinValidator()
        );
        terminal.run();
    }
}
