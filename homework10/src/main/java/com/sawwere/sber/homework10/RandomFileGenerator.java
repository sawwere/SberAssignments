package com.sawwere.sber.homework10;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;

public class RandomFileGenerator {
    public RandomFileGenerator() {
    }

    public static void create(Path path, int count) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(String.valueOf(path))));) {
            for (int i = 0; i < count; ++i) {
                writer.write(nextRandom(1, 50) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int nextRandom(int from, int to) {
        return (int)(Math.random() * (double)(to - from) + (double)from);
    }
}