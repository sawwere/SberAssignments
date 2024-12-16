package com.sawwere.sber.homework9;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * A class that implements the operation of the filter stream.
 * It is an iterator that skips the elements of the parent stream using the passed predicate.
 * @param <T> type of elements in the stream
 */
public class FilterStream<T> extends Streams<T> {

    private final Streams<T> parent;
    private final Predicate<? super T> filter;
    /**
     * Marker representing that current item has been tested by the filtering predicate
     * and thus should be included in the output(filtered) stream
      */
    private boolean isAlreadyTested;
    /**
     * The next element in the filtered stream
     */
    private T filteredNext;

    protected FilterStream(Streams<T> parent, Predicate <? super T> filter) {
        this.parent = parent;
        this.filter = filter;
        this.isAlreadyTested = false;
    }

    /**
     * Skips elements it the stream until one that satisfies filtering condition is found
     * @return true if there is a next element in the filtered stream
     */
    private boolean lookForNext() {
        do {
            if (!parent.hasNext()) {
                return false;
            }
            filteredNext = parent.next();
        } while (!filter.test(filteredNext));
        isAlreadyTested = true;
        return true;
    }

    @Override
    public boolean hasNext () {
        return isAlreadyTested || lookForNext();
    }

    @Override
    public T next () {
        if (hasNext()) {
            isAlreadyTested = false;
            return filteredNext;
        } else {
            throw new NoSuchElementException();
        }
    }
}
