package com.sawwere;

import java.util.Map;

public interface CountMap<V> {
    // добавляет элемент в этот контейнер.
    void add(V o);

    //Возвращает количество добавлений данного элемента
    int getCount(V o);

    //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
    int remove(V o);

    //количество разных элементов
    int size();

    //Добавить все элементы из source в текущий контейнер,
    // при совпадении ключей,     суммировать значения
    void addAll(CountMap<V> source);

    //Вернуть java.util.Map. ключ - добавленный элемент,
    // значение - количество его добавлений
    Map<V, Integer> toMap();

    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    void toMap(Map<V, Integer> destination);
}
