package com.sawwere.sber.homework14;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    /**
     * Тип хранилища кеша.
     */
    CacheType cacheType() default CacheType.IN_MEMORY;

    /**
     * Префикс, который будет использован в случае кеширования метода в виде файла.
     */
    String fileNamePrefix() default "";

    /**
     * Нужно ли использовать сжатие в zip архив в случае кеширования метода в виде файла.
     */
    boolean zip() default false;

    /**
     * Сколько элементов списка сохранять в кеше, если результат метода - это List.
     * Оставшиеся элементы игнорируются.
     */
    int maxListSize() default 0;

    /**
     * Какие аргументы метода учитывать при определении уникальности результата,
     * а какие игнорировать(по умолчанию все аргументы учитываются).
     * Уникальность аргумента определяется по его значению его аргументов любого из указанных классов.
     */
    Class<?>[] identityBy() default {};
}
