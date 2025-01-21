package com.sawwere.sber.homework14.storage.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Простой объект для хранения информации о кешируемых метода.
 * Введен с целью возможности хранения в базе методов, возвращающих null
 * (иначе неудобно проверять присутствия в бд:
 *      метода может не быть в кеше / метод есть и возвращает null / метод есть и возвращает не null
 * - нужна обертка для разграничения первых 2 ситуаций)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CachedMethodEntity {
    // имя метода - класс + название метода
    private String name;
    // Набор значений ключевых аргументов метода, по которым идет кеширование.
    // Определяется в аннотации метода
    private List<Object> keyArgs;
    // Результат метода
    private Object result;
}
