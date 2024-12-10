package com.sawwere.sber.homework7.rps;

import com.sawwere.sber.homework7.rps.action.RPSAction;
import com.sawwere.sber.homework7.rps.action.RPSActionComparator;
import com.sawwere.sber.homework7.rps.exception.TournamentException;
import com.sawwere.sber.homework7.rps.player.Player;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Tournament {
    private static final int ROUNDS_TO_PLAY = 3;
    private final RPSActionComparator actionComparator = new RPSActionComparator();
    private final PlayerLoader playerLoader;
    private final Iterator<String> jarFileIterator;
    // Для хранения ссылки на плагин-победителя прошлых раундов
    // и предотвращения его выгрузки
    private Player lastWinner;
    // Для хранения имени плагина-победителя прошлых раундов
    // (имени файла, из которого он был загружен)
    private String lastWinnerName;

    public Tournament(PlayerLoader playerLoader) {
        this.playerLoader = playerLoader;

        try {
            List<String> jarFilenames = playerLoader.getAllJars();
            if(jarFilenames.size() < 2) {
                throw new TournamentException("To start must have at least 2 .jar files");
            }
            jarFileIterator = jarFilenames.iterator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lastWinnerName = jarFileIterator.next();
        lastWinner = playerLoader.loadPlayer(lastWinnerName);
    }

    /**
     * Начинает соревнование между плагинами-игроками
     */
    public void run() {
        System.out.println("Начало смертельной битвы!");
        while(jarFileIterator.hasNext()) {
            nextMatch();
        }
        System.out.println("Победитель турнира: " + lastWinnerName);
    }

    /**
     * Проводит очередной матч между прошлым победителем и следующим игроком
     */
    public void nextMatch() {
        String secondPlayerName = jarFileIterator.next();
        Player secondPlayer = playerLoader.loadPlayer(secondPlayerName);
        System.out.println("=======================");
        System.out.println("Приветствуем игрока 1: " + lastWinnerName);
        System.out.println("Приветствуем игрока 2: " + secondPlayerName);
        int total = 0;
        for (int i = 0; i < ROUNDS_TO_PLAY; i++) {
            System.out.printf("ROUND %d FIGHT!%n", i+1);
            int result = 0;
            while (result == 0) {
                RPSAction firstPlayerChoice = lastWinner.play();
                RPSAction secondPlayerChoice = secondPlayer.play();
                System.out.println("Игрок 1 использует: " + firstPlayerChoice.toString());
                System.out.println("Игрок 2 использует: " + secondPlayerChoice.toString());
                result = actionComparator.compare(firstPlayerChoice, secondPlayerChoice);
                if (result == 0) {
                    System.out.println("Ничья, повтор раунда");
                }
            }
            if (result > 0) {
                System.out.printf("Выиграл игрок под именем %s!%n", lastWinnerName);
            } else {
                System.out.printf("Выиграл игрок под именем %s!%n", secondPlayerName);
            }
            total += result;
        }
        if (total < 0) {
            lastWinnerName = secondPlayerName;
            lastWinner = secondPlayer;
        }
        System.out.println("Победа игрока " + lastWinnerName);
        System.out.println("=======================");
    }
}
