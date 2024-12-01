package com.sawwere.sber.homework5;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ReflectionUtils {
    /**
     * Возвращает все методы класса(включая приватные)
     * @param c заданный класс
     * @return список методов
     */
    public static List<Method> getAllMethods(Class<?> c) {
        List<Method> result = new ArrayList<>();
        var methods = c.getDeclaredMethods();
        while (c != null) {
            Collections.addAll(result, methods);
            c = c.getSuperclass();
        }
        return result;
    }

    /**
     * Выводит на консоль все методы класса, включая все родительские методы
     * (Задача 2)
     * @param c заданный класс
     */
    public static void printAllMethods(Class<?> c) {
        for (var method : getAllMethods(c)) {
            System.out.println(method);
        }
    }

    /**
     * Находит все геттеры класса, то есть public методы, имя которых начинается с 'get'
     * @param c заданный класс
     * @return список методов-геттеров
     */
    public static List<Method> getAllGetters(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot find getters for null object");
        }
        return Arrays.stream(c.getMethods()).filter(x -> x.getName().startsWith("get")).toList();
    }

    /**
     * Находит все сеттеры класса, то есть public методы, имя которых начинается с 'set'
     * @param c заданный класс
     * @return список методов-сеттеров
     */
    public static List<Method> getAllSetters(Class<?> c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot find setters for null object");
        }
        return Arrays.stream(c.getMethods()).filter(x -> x.getName().startsWith("set")).toList();
    }

    /**
     * Проверяет, что все String константы имеют значение = их имени
     * @param o заданный объект
     * @return true, если все String константы имеют значение = их имени
     */
    public static boolean checkStringConstantsEqualsNames(Object o) {
        for (Field field : o.getClass().getDeclaredFields()) {
            int modifier = field.getModifiers();
            if (field.getType().getName().equals("java.lang.String")
                    && Modifier.isFinal(modifier)) {
                try {
                    field.setAccessible(true);
                    if (!field.getName().equals(field.get(o))) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }
}
