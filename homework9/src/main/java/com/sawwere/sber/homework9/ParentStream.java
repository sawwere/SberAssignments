package com.sawwere.sber.homework9;

import java.util.Collection;
import java.util.Iterator;

public class ParentStream<T> extends Streams<T> {

    private final Iterator<? extends T> sourceDataIterator;

    protected ParentStream(Collection<? extends T> collection) {
        sourceDataIterator = collection.iterator();
    }

    @Override
    public T next() {
        return sourceDataIterator.next();
    }

    @Override
    public boolean hasNext() {
        return sourceDataIterator.hasNext();
    }
}
