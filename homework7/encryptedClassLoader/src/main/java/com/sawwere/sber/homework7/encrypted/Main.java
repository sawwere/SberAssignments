package com.sawwere.sber.homework7.encrypted;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        String encryptionKey = "secretKey";
        Encoder encoder = new SecretEncoder();

        try {
            String sourceFilePath = "./encryptedClassLoader/src/main/resources/";
            FileUtils.encodeAndSave(sourceFilePath, encoder, encryptionKey);

            EncryptedClassLoader classLoader = new EncryptedClassLoader(
                    encryptionKey,
                    new File(sourceFilePath+"/encoded"),
                    EncryptedClassLoader.class.getClassLoader(),
                    encoder
            );

            Class<?> loadedClass = classLoader.findClass("com.sawwere.sber.homework7.encrypted.instance.Sample");

            Object instance = loadedClass.getDeclaredConstructor().newInstance();
            loadedClass.getMethod("printMessage").invoke(instance);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException | SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

