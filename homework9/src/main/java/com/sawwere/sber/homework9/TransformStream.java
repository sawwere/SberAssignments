package com.sawwere.sber.homework9;

import java.util.function.Function;

/**
 *  A class that implements the operation of the transform stream.
 *  It is an iterator that returns the elements of the parent stream
 *  transformed with the passed function.
 * @param <T> type of elements in the parent stream
 * @param <R> type of elements in the result stream
 */
class TransformStream<T, R> extends Streams<R> {

    private final Streams<T> parent;
    private final Function<? super T, ? extends R> transform;

    protected TransformStream(Streams<T> parent, Function<? super T, ? extends R> transform) {
        this.parent = parent;
        this.transform = transform;
    }

    @Override
    public boolean hasNext() {
        return parent.hasNext();
    }

    @Override
    public R next() {
        return transform.apply(parent.next());
    }
}
