package com.sawwere.sber.homework7;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sawwere.sber.homework7.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class PluginManagerTest {
    private static PluginManager pluginManager;

    @BeforeAll
    public static void initialize() {
        pluginManager = new PluginManager(pluginRootDirectory);
    }

    @DisplayName("Проверка загрузки плагинов с успешным завершением")
    @Test
    void shouldLoadPluginsWhichExistInJarFile() {
        try {
            Plugin plugin1 = pluginManager.load(jarFileNameExisting, correctPluginName1);

            assertNotNull(plugin1);
            assertEquals(plugin1.getClass().getName(), "com.sawwere.sber.homework7.MyPlugin1");

            Plugin plugin2 = pluginManager.load(jarFileNameExisting, correctPluginName2);

            assertNotNull(plugin2);
            assertEquals(plugin2.getClass().getName(), "com.sawwere.sber.homework7.MyPlugin2");

        } catch (PluginLoadException exception) {
            fail("Не ожидалось исключение: " + exception.getMessage());
        }
    }

    @DisplayName("Выброс исключения, если запрашиваемый плагин не имплементирует интерфейс Plugin")
    @Test
    void shouldThrowExceptionIfClassDoesNotImplementPlugin() {
        assertThrows(PluginLoadException.class, () -> {
            Plugin plugin = pluginManager.load(jarFileNameExisting, inCorrectPluginName1);
        });
    }

    @DisplayName("Выброс исключения, если запрашиваемый плагин не существует в текущем JAR файле")
    @Test
    void shouldThrowExceptionIfClassDoesNotExist() {
        assertThrows(PluginLoadException.class, () -> {
            Plugin plugin = pluginManager.load(jarFileNameExisting, inCorrectPluginName2);
        });
    }

    @DisplayName("Выброс исключения, если запрашиваемый JAR файл не существует")
    @Test
    void shouldThrowExceptionIfJarFileDoesNotExist() {
        assertThrows(PluginLoadException.class, () -> {
            Plugin plugin = pluginManager.load(jarFileNameNotExisting, correctPluginName1);
        });
    }
}