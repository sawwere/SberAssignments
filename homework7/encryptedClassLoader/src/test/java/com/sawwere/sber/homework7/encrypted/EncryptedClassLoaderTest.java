package com.sawwere.sber.homework7.encrypted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;

class EncryptedClassLoaderTest {
    private static final Encoder encoder = new SecretEncoder();
    // Для запуска нужно выставить в конфигурации запуска теста
    // Working directory: $MODULE_WORKING_DIR$
    private static final Path rootDir = Path.of("src/test/resources/");
    private static final String encryptionKey = "secret";

    @BeforeAll
    public static void initialize() throws IOException {
        FileUtils.encodeAndSave(String.valueOf(rootDir), encoder, encryptionKey);
    }

    @Test
    void findClass() throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        // given
        EncryptedClassLoader classLoader = new EncryptedClassLoader(
                "secret",
                rootDir.resolve("encoded").toFile(),
                EncryptedClassLoader.class.getClassLoader(),
                encoder
        );
        Class<?> c = classLoader.findClass("com.sawwere.sber.homework7.encrypted.data.TestClass");
        Field field = c.getDeclaredField("wish");
        field.setAccessible(true);
        String actual = (String)field.get(c);

        // then
        Assertions.assertEquals("I want to be encrypted!", actual);
    }
}