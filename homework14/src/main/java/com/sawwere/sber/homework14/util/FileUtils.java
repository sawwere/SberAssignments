package com.sawwere.sber.homework14.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public final class FileUtils {
    public static void createEmptyFile(Path path) throws IOException {
        Files.deleteIfExists(path);
        Files.createFile(path);
    }

    public static void cleanDirectory(Path path) throws IOException {
        try (Stream<Path> stream = Files.walk(path, 1)) {
            stream.filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
