package com.sawwere.sber.homework6.iterator;

import org.junit.jupiter.api.Assertions;

import java.util.NoSuchElementException;
class ArrayIteratorTest {

    @org.junit.jupiter.api.Test
    void hasNext() {
        Integer[] arr = {1,2,0,45,123,-3,12};
        ArrayIterator<Integer> underTest = new ArrayIterator<>(arr);
        Assertions.assertTrue(underTest.hasNext());
    }

    @org.junit.jupiter.api.Test
    void hasNextOnEmptyArray() {
        Integer[] arr = {};
        ArrayIterator<Integer> underTest = new ArrayIterator<>(arr);
        Assertions.assertFalse(underTest.hasNext());
    }

    @org.junit.jupiter.api.Test
    void hasNextAtTheEnd() {
        Integer[] arr = {1,2,0,45,123,-3,12};
        ArrayIterator<Integer> underTest = new ArrayIterator<>(arr);
        Assertions.assertTrue(underTest.hasNext());
    }

    @org.junit.jupiter.api.Test
    void next() {
        Integer[] arr = {1,2,0,45,123,-3,12};
        ArrayIterator<Integer> underTest = new ArrayIterator<>(arr);
        Assertions.assertEquals(1, underTest.next());
    }

    @org.junit.jupiter.api.Test
    void nextOnEmptyArray() {
        Integer[] arr = {};
        ArrayIterator<Integer> underTest = new ArrayIterator<>(arr);
        Assertions.assertThrows(NoSuchElementException.class, () -> underTest.next());
    }

    @org.junit.jupiter.api.Test
    void nextLoop() {
        Integer[] arr = {1,2,0,45,123,-3,12};
        ArrayIterator<Integer> underTest = new ArrayIterator<>(arr);
        int iter = 0;
        while (underTest.hasNext()) {
            Assertions.assertEquals(arr[iter++], underTest.next());
        }
        Assertions.assertThrows(NoSuchElementException.class, () -> underTest.next());
    }
}