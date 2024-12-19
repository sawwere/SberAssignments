package com.sawwere.sber.homework10;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void mainTaskDemo() {
        Path path = Path.of("demo.txt");
        RandomFileGenerator.create(path, 5000);

        try {
            Factorial.parallelFactorial(path);
        } catch (InterruptedException | IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static void main(String[] args) {
        mainTaskDemo();
        BonusTask.print();
    }
}
