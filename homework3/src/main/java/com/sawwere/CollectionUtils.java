package com.sawwere;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionUtils {
    /**
     * Добавляет все элементы из первого списка (source) во второй список (destination).
     *
     * @param source      Список элементов, которые будут добавлены.
     * @param destination Список, в который будут добавлены элементы.
     */
    public static<T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    /**
     * Создает новый пустой список.
     *
     * @return Новый пустой список.
     */
    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * Возвращает индекс первого вхождения элемента в списке.
     *
     * @param source Список, в котором производится поиск.
     * @param o      Элемент, индекс которого нужно найти.
     * @return Индекс первого вхождения элемента, или -1, если элемент не найден.
     */
    public static <T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    /**
     * Ограничивает список до заданного размера.
     *
     * @param source Исходный список.
     * @param size   Максимальное количество элементов.
     * @return Новый список, содержащий только первые size элементов из исходного списка.
     */
    public static <T> List<T> limit(List<T> source, int size) {
        return source.subList(0, size);
    }

    /**
     * Добавляет указанный элемент в список.
     *
     * @param source Список, в который будет добавлен элемент.
     * @param o      Элемент, который необходимо добавить.
     */
    public static <T> void add(List<? super T> source, T o) {
        source.add(o);
    }

    /**
     * Удаляет все элементы из второго списка (c2) из первого списка (removeFrom).
     *
     * @param removeFrom Список, из которого будут удалены элементы.
     * @param c2        Список элементов, которые будут удалены.
     */
    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    /**
     * Проверяет, содержит ли первый список все элементы второго списка.
     *
     * @param c1 Первый список.
     * @param c2 Второй список.
     * @return true, если первый список содержит все элементы второго, иначе false.
     */
    public static <T> boolean containsAll(List<? extends T> c1, List<? extends T> c2) {
        for (T elem : c2) {
            if (!c1.contains(elem)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяет, содержит ли первый список хотя бы один элемент из второго списка.
     *
     * @param c1 Первый список.
     * @param c2 Второй список.
     * @return true, если первый список содержит хотя бы один элемент второго, иначе false.
     */
    public static <T> boolean containsAny(List<? extends T> c1, List<? extends T> c2) {
        for (T elem : c2) {
            if (c1.contains(elem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
     * Элементы сравнивать через Comparable.
     * Пример range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}
     * @param list Входной список.
     * @param min  Минимальное значение (включается).
     * @param max  Максимальное значение (включается).
     * @return Новый список, содержащий элементы в заданном диапазоне.
     */
    public static <T extends Comparable<? super T>> List<T> range(List<? extends T> list, T min, T max) {
        return list.stream()
                .filter(x -> x.compareTo(min) >= 0 && x.compareTo(max) <= 0)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
     * Элементы сравнивать через Comparator.
     * Пример range(Arrays.asList(8,1,3,5,6, 4), 3, 6) вернет {3,4,5,6}
     * @param list      Входной список.
     * @param min       Минимальное значение (включается).
     * @param max       Максимальное значение (включается).
     * @param comparator Компаратор, используемый для сравнения элементов.
     * @return Новый список, содержащий элементы в заданном диапазоне.
     */
    public static <T> List<T> range(List<? extends T> list, T min, T max, Comparator<? super T> comparator) {
        return list.stream()
                .filter(x -> comparator.compare(x, min) >= 0 && comparator.compare(x, max) <= 0)
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
