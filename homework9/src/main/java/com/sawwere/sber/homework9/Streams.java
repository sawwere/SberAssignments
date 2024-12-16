package com.sawwere.sber.homework9;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class Streams<T> implements Iterator<T> {
    /**
     * Creates a new Streams object consisting of elements of the source collection
     * @param collection source collection
     * @return new Streams object
     */
    public static <T> Streams<T> of(Collection<? extends T> collection) {
        return new ParentStream<>(collection);
    }

    /**
     * Returns new Streams object with elements in the collection that satisfy the condition of the predicate
     * @param predicate filtering function
     * @return new Streams object
     */
    public Streams<T> filter(Predicate<? super T> predicate) {
        return new FilterStream<>(this, predicate);
    }

    /**
     * Returns new Streams object with elements in the collection transformed with the mapper function
     * @param mapper mapper function
     * @return new Streams object
     */
    public <R> Streams<R> transform(Function<? super T, ? extends R> mapper) {
        return new TransformStream<>(this, mapper);
    }

    /**
     * Accumulates elements into a Map whose keys and values are the result of
     * applying the provided mapping functions to the input elements.
     * <p> This is a terminal operation.
     * @throws IllegalStateException if a collision between keys happens
     * @param keyMapper  a mapping function to produce keys
     * @param valueMapper a mapping function to produce keys
     * @return new Map - the result of the accumulation
     */
    public final <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                        Function<? super T, ? extends V> valueMapper) {
        var result = new HashMap<K, V>();

        while (hasNext()) {
            var next = next();
            var key = keyMapper.apply(next);
            var value = valueMapper.apply(next);
            var oldValue = result.putIfAbsent(key, value);
            if (oldValue != null) {
                throw new IllegalStateException(
                        "Attempted to put multiply values(%s and %s) for key %s".formatted(value, oldValue, key)
                );
            }
            result.put(key, value);
        }
        return result;
    }
}

