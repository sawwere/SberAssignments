package com.sawwere.sber.homework7.rps.player;

import com.sawwere.sber.homework7.rps.action.RPSAction;

public class PaperPlayer implements Player {
    @Override
    public RPSAction play() {
        return RPSAction.PAPER;
    }
}