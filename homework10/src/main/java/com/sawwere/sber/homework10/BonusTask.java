package com.sawwere.sber.homework10;

public class BonusTask {
    private static volatile boolean isNumberTurn = true;

    /**
     * Prints string '0 a 1 b 2 c 3 d 4 e 5 f 6 g 7 h 8 i 9 j'
     * using 2 threads without imbdeeded synchronization objects
     */
    public static void print() {
        System.out.println("================================");
        System.out.println("|            Bonus             |");
        System.out.println("================================");
        Thread numberThread = new NumberThread();
        Thread letterThread = new LetterThread();
        numberThread.start();
        letterThread.start();
    }

    /**
     * Simple threat printing numbers from 0 to 9 between letters
     */
    private static class NumberThread extends Thread {
        public void run() {
            for(int i = 0; i <= 9; ++i) {
                while(!BonusTask.isNumberTurn) {
                    Thread.onSpinWait();
                }

                System.out.print(i + " ");
                BonusTask.isNumberTurn = false;
            }
        }
    }

    /**
     * Simple threat printing letters from a to j between numbers
     */
    private static class LetterThread extends Thread {
        public void run() {
            for(char c = 'a'; c <= 'j'; ++c) {
                while(BonusTask.isNumberTurn) {
                    Thread.onSpinWait();
                }

                System.out.print(c + " ");
                BonusTask.isNumberTurn = true;
            }
        }
    }
}
