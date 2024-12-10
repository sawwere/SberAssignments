package com.sawwere.sber.homework7;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Загрузчик классов с измененным механизмом делегирования.
 * Действует противоположно принятой модели -
 * сначала сам пытается загрузить класс, и если не получилось,
 * то делегирует эту задачу родительскому загрузчику.
 */
public class MyURLClassLoader extends URLClassLoader {
    public MyURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> loadedClass = super.findLoadedClass(name);
        if (loadedClass != null) {
            return loadedClass;
        }

        try {
            return super.findClass(name);
        } catch (ClassNotFoundException e) {
            return super.loadClass(name);
        }
    }
}
