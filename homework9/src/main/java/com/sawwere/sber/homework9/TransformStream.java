package com.sawwere.sber.homework9;

import java.util.function.Function;


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
