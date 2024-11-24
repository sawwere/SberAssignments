package com.sawwere;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CollectionUtilsTest {

    @Test
    void addAll() {
        List<Integer> src = new ArrayList<>(List.of(16, 23, 42));
        List<Integer> dest = new ArrayList<>(List.of(4, 8, 15));
        CollectionUtils.addAll(src, dest);

        List<Integer> expected = List.of(4, 8, 15, 16, 23, 42);
        assertEquals(expected, dest);
    }

    @Test
    void newArrayList() {
        List<Integer> actual = CollectionUtils.newArrayList();

        List<Integer> expected = Collections.emptyList();
        assertEquals(expected, actual);
    }

    @Test
    void indexOf() {
        List<Integer> list = new ArrayList<>(List.of(16, 23, 42, 22, 48, 23, 1));

        int actual = CollectionUtils.indexOf(list, 42);
        assertEquals(2, actual);
    }

    @Test
    void limit() {
        List<Integer> list = new ArrayList<>(List.of(16, 23, 42, 22, 48, 23, 1));

        List<Integer> expected = List.of(16, 23, 42, 22);
        List<Integer> actual = CollectionUtils.limit(list, 4);

        assertEquals(expected, actual);
    }

    @Test
    void add() {
        List<Integer> underTest = new ArrayList<>(List.of(16, 23, 42, 22, 48, 23, 1));

        List<Integer> expected = List.of(16, 23, 42, 22, 48, 23, 1, -8);
        CollectionUtils.add(underTest, -8);

        assertEquals(expected, underTest);
    }

    @Test
    void removeAll() {
        List<Integer> underTest = new ArrayList<>(List.of(16, 23, 42, 22, 48, 23, 1));
        List<Integer> removeItems = new ArrayList<>(List.of(4, 16, 23, 1));
        CollectionUtils.removeAll(underTest, removeItems);

        List<Integer> expected = List.of(42, 22, 48);
        assertEquals(expected, underTest);
    }

    @Test
    void containsAll() {
        List<Integer> underTest = new ArrayList<>(List.of(16, 23, 42, 22, 23, 1));
        List<Integer> container = new ArrayList<>(List.of(1, 23, 16, 22, 42));

        assertTrue(CollectionUtils.containsAll(underTest, container));
    }

    @Test
    void containsAllFail() {
        List<Integer> underTest = new ArrayList<>(List.of(16, 23, 42, 22, 23, 1));
        List<Integer> container = new ArrayList<>(List.of(1, 23, 16, 22, 42, 33));

        assertFalse(CollectionUtils.containsAll(underTest, container));
    }

    @Test
    void containsAny() {
        List<Integer> underTest = new ArrayList<>(List.of(16, 23, 42, 22, 23, 1));
        List<Integer> container = new ArrayList<>(List.of(-1, 15, 16, 420));

        assertTrue(CollectionUtils.containsAny(underTest, container));
    }

    @Test
    void containsAnyFail() {
        List<Integer> underTest = new ArrayList<>(List.of(16, 23, 42, 22, 23, 1));
        List<Integer> container = new ArrayList<>(List.of(-1, 105, 106, 420));

        assertFalse(CollectionUtils.containsAny(underTest, container));
    }

    @Test
    void rangeWithComparable() {
        List<Integer> underTest = new ArrayList<>(List.of(16, 23, 42, 22, 48, 23, 1));
        List<Integer> actual = CollectionUtils.range(underTest,3, 23);

        List<Integer> expected = List.of(16, 22, 23, 23);
        assertEquals(expected, actual);
    }

    @Test
    void rangeWithComparator() {
        List<Integer> underTest = new ArrayList<>(List.of(16, 23, 42, 22, 48, 23, 1));
        Comparator<Integer> comparator = Comparator.naturalOrder();
        List<Integer> actual = CollectionUtils.range(underTest,3, 23, comparator);

        List<Integer> expected = List.of(16, 22, 23, 23);
        assertEquals(expected, actual);
    }
}