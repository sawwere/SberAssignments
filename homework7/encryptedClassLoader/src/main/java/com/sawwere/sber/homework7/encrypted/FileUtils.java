package com.sawwere.sber.homework7.encrypted;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public final class FileUtils {
    public static void encodeAndSave(String path, Encoder encoder, String key) throws IOException {
        Files.createDirectories(Path.of(path + "/encoded"));
        try (Stream<Path> stream = Files.walk(Paths.get(path), 1)) {
            stream.filter(x -> Files.isRegularFile(x))
                    .forEach(x-> {
                        System.out.println("Encoding file " + x.getFileName());
                        try {
                            byte[] bytes = Files.readAllBytes(x);

                            // Сохранение зашифрованных байтов в файл
                            Path encryptedFilePath = Path.of(path + "/encoded")
                                    .resolve(x.getFileName() +".class");
                            Files.write(encryptedFilePath, encoder.encode(bytes, key));
                        } catch (IOException e) {
                            System.err.println("Error while reading file " + x.getFileName());;
                        }
                    });
        }
    }
}
