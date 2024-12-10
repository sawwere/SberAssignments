package com.sawwere.sber.homework7.rps.action;

import com.sawwere.sber.homework7.rps.action.RPSAction;

import java.util.Comparator;

public class RPSActionComparator implements Comparator<RPSAction> {
    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(RPSAction o1, RPSAction o2) {
        if (o1 == o2) {
            return 0;
        }
        if ((o1 == RPSAction.ROCK && o2 == RPSAction.PAPER)
            || (o1 == RPSAction.PAPER && o2 == RPSAction.SCISSORS)
            || (o1 == RPSAction.SCISSORS && o2 == RPSAction.ROCK)) {
            return -1;
        }
        return 1;
    }
}
