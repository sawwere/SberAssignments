package com.sawwere.sber.homework13.util;

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

    public static Object deserialize(Path loadFrom) throws IOException, ClassNotFoundException {
        try(FileInputStream fileInputStream = new FileInputStream(loadFrom.toString())) {
            return deserialize(fileInputStream);
        }
    }

    public static Object deserialize(InputStream in) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            return objectInputStream.readObject();
        }
    }

    public static void serialize(OutputStream out, Object obj) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            objectOutputStream.writeObject(obj);
        }
    }
}
