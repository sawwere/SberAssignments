package com.sawwere;

import java.util.List;


public class Main {
    public static void countMapDemo() {
        System.out.println("=======================");
        System.out.println("|        Task 1       |");
        System.out.println("=======================");
        CountMap<Integer> map = new CountMapImpl<>();

        map.add(10);
        map.add(10);
        map.add(5);
        map.add(6);
        map.add(5);
        map.add(10);

        System.out.printf("map.getCount(5) = %s%n", map.getCount(5));  // 2
        System.out.printf("map.getCount(6) = %s%n", map.getCount(6));
        System.out.printf("map.getCount(10) = %s%n", map.getCount(10));
        System.out.printf("map.getCount(-100) = %s%n", map.getCount(-100));
    }

    public static void collectionUtilsDemo() {
        // Демонстрация того, что программа компилируется при работе со списками объектов Родителей и Потомков
        // Проверка работы методов находится в соответствующих тестах
        System.out.println("=======================");
        System.out.println("|        Task 2       |");
        System.out.println("=======================");

        List<Apple> apples = CollectionUtils.newArrayList();
        apples.add(new Apple("apple1"));

        List<? extends Fruit> basket = apples;

        List<Fruit> fruits = CollectionUtils.newArrayList();
        CollectionUtils.indexOf(fruits, new Apple("apple2"));

        CollectionUtils.add(fruits, new Fruit("fruit1"));
        CollectionUtils.addAll(apples, fruits);
        System.out.println(fruits);
        // CollectionUtils.addAll(fruits, apples); // error

        CollectionUtils.removeAll(fruits, apples);

        boolean temp = CollectionUtils.containsAll(apples, fruits);
        temp = CollectionUtils.containsAny(apples, basket);
    }

    public static void main(String[] args) {
        countMapDemo();

        collectionUtilsDemo();
    }
}