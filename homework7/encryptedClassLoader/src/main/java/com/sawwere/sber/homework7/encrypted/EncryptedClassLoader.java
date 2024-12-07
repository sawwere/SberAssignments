package com.sawwere.sber.homework7.encrypted;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EncryptedClassLoader extends ClassLoader {
    private final File dir;
    private final String key;
    private final Encoder encoder;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent, Encoder encoder) {
        super(parent);
        this.key = key;
        this.dir = dir;
        this.encoder = encoder;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // Получаем название файла из имени класса путем удаления названия пакета
            int dotInd = name.lastIndexOf('.');
            String fileName = name;
            if (dotInd > 0) {
                fileName = fileName.substring(dotInd + 1, name.length());
            }
            Path filePath = dir.toPath().resolve(fileName + ".class");

            byte[] bytes = Files.readAllBytes(filePath);
            bytes = encoder.decode(bytes, key);
            // для демонстрации декодирования
            // Files.write(Path.of(filePath + ".class"), bytes);

            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException exception) {
            throw new ClassNotFoundException(String.format("Ошибка загрузки класса: %s", name), exception);
        }
    }
}
