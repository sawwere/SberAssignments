package com.sawwere.sber.homework4;

import com.sawwere.sber.homework4.terminal.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Main {
    public static void task1() {
        System.out.println("=======================");
        System.out.println("|        Task 1       |");
        System.out.println("=======================");

        String correctPin = "2214";

        Terminal terminal = new TerminalImpl(
                new TerminalServerImpl(correctPin),
                new TerminalConsoleDisplay(),
                new PinValidator(),
                System.in
        );
        terminal.run();
    }

    public static void task2() {
        System.out.println("=======================");
        System.out.println("|        Task 2       |");
        System.out.println("=======================");
        ContentReader contentReader = new ContentReader();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Для выхода введите 0");
                System.out.println("Введите ссылку:");
                String link = scanner.nextLine();
                if (link.equals("0")) {
                    break;
                }
                String content = contentReader.readContent(link);
                System.out.println("Загружено содержимое ресурса:\n");
                System.out.println(content);
                break;
            } catch (MalformedURLException e) {
                System.err.println("Введен некорректный адрес, повторите попытку.");
            } catch (IOException e) {
                System.err.println("Не удалось подключиться к заданному ресурсу.");
            }
        }
    }

    public static void main(String[] args) {
        task1();

        task2();
    }
}
