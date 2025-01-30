package com.sawwere.sber.homework17.util;

import java.util.Scanner;

/**
 * Вспомогательные методы для чтения данных с консоли.
 * Ввод повторяется до тех пор, пока не будет введено требуемое значение.
 */
public final class ConsoleUtils {
    public static Long readLong(String message, Scanner scanner) {
        while (true) {
            try {
                System.out.print(message);
                return Long.valueOf(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Некорректный ввод, ожидалось целое число.");
            }
        }
    }

    public static Integer readInt(String message, Scanner scanner) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.valueOf(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Некорректный ввод, ожидалось целое число.");
            }
        }
    }

    public static String readString(String message, Scanner scanner) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (!input.isBlank()) {
                return input;
            }
        }
    }
}
