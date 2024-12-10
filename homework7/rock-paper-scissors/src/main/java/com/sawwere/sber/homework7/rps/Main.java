package com.sawwere.sber.homework7.rps;

import java.nio.file.Path;


public class Main {
    public static void main( String[] args ) {
        Path jarDirectory = Path.of("./rock-paper-scissors/src/main/resources");
        Tournament tournament = new Tournament(new PlayerLoader(jarDirectory));
        tournament.run();
    }
}
