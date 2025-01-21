package com.sawwere.sber.homework14.util;

import java.io.*;
import java.nio.file.Path;

/**
 * Utility класс, предоставляющий методы для (де)сериализации данных.
 */
public final class SerializationUtils {

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

    public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            serialize(outputStream, obj);
            return outputStream.toByteArray();
        }
    }
}
