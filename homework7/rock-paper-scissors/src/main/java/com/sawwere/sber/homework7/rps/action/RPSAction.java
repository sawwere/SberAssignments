package com.sawwere.sber.homework7.rps.action;

public enum RPSAction {
    ROCK("камень"),
    PAPER("бумага"),
    SCISSORS("ножницы");

    private final String name;

    RPSAction(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}
