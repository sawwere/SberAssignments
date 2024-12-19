package com.sawwere.sber.homework10;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Factorial {
    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static BigInteger factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Can't calculate factorial for negative numbers");
        }
        BigInteger res = BigInteger.ONE;

        for(int i = 1; i <= n; ++i) {
            res = res.multiply(BigInteger.valueOf(i));
        }

        return res;
    }

    private static synchronized Integer readNextInt(Scanner scanner) {
        return scanner.hasNextInt() ? scanner.nextInt() : null;
    }

    public static void parallelFactorial(Path path) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(path);
        List<FactorialThread> threads = new ArrayList<>();

        for(int i = 0; i < THREAD_COUNT; ++i) {
            threads.add(new FactorialThread(scanner, "Thread-" + i));
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static class FactorialThread extends Thread {
        private final Scanner scanner;
        private final String name;

        FactorialThread(Scanner scanner, String name) {
            this.scanner = scanner;
            this.name = name;
        }

        public void run() {
            while(true) {
                Integer num = Factorial.readNextInt(this.scanner);
                if (num == null) {
                    return;
                }

                System.out.println("%d! = %s ---- %s".formatted(num, Factorial.factorial(num), this.name));
            }
        }
    }
}
