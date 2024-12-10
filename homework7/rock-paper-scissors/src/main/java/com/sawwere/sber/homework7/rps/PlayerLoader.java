package com.sawwere.sber.homework7.rps;

import com.sawwere.sber.homework7.rps.player.Player;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class PlayerLoader {
    private final Path playerRootDirectory;

    public PlayerLoader(Path playerRootDirectory) {
        this.playerRootDirectory = playerRootDirectory;
    }

    /**
     * Ищет и загружает первый подходящий класс игрока из заданного jar-файла
     * (расчет на то, что пользователи могут назвать свою реализацию неизвестным заранее именем)
     *
     * @param jarName имя jar-файла
     * @return экземпляр загруженного класса
     */
    public Player loadPlayer(String jarName) {
        var pathToTargetClassJar = playerRootDirectory.resolve(jarName + ".jar");

        try (URLClassLoader classLoader = new URLClassLoader(new URL[] {pathToTargetClassJar.toUri().toURL()});
                JarFile targetClassJar = new JarFile(pathToTargetClassJar.toFile())) {

            for (Enumeration<JarEntry> entry = targetClassJar.entries(); entry.hasMoreElements(); ) {
                JarEntry jarEntry = entry.nextElement();
                if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
                    continue;
                }
                String fileName = jarEntry.getName()
                        .replace('/', '.')
                        .substring(0, jarEntry.getName().length() - 6); // skip '.class' at the end
                Class<?> c;
                try {
                    c = classLoader.loadClass(fileName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (!Player.class.isAssignableFrom(c) || c.isInterface()) {
                    continue;
                }
                try {
                    return (Player) c.getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException(jarName);
    }

    /**
     * Возвращает список имен jar-файлов, находящихся в корневом каталоге
     * @return новый список
     */
    public List<String> getAllJars() throws IOException {
        try (Stream<Path> stream = Files.walk(playerRootDirectory, 1)) {
            return stream
                .filter(x -> Files.isRegularFile(x) && x.getFileName().toString().endsWith(".jar"))
                // skip '.jar' at the end
                .map(x -> x.getFileName().toString().substring(0, x.getFileName().toString().length() - 4))
                .toList();
        }
    }
}
