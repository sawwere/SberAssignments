package com.sawwere.sber.homework7.rps.player;

import com.sawwere.sber.homework7.rps.action.RPSAction;

public class PlayerImpl implements Player {
    @Override
    public RPSAction play() {
        RPSAction[] values = RPSAction.values();

        int selectedIndex = (int) (Math.random() * values.length);

        return values[selectedIndex];
    }
}
