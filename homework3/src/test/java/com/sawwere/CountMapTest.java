package com.sawwere;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CountMapTest {

    @Test
    void add() {
        CountMap<String> underTest = new CountMapImpl<>();
        Assertions.assertEquals(0, underTest.getCount("test"));
        underTest.add("test");
        Assertions.assertEquals(1, underTest.getCount("test"));
        underTest.add("test");
        underTest.add("test");
        Assertions.assertEquals(3, underTest.getCount("test"));
        underTest.add("another");
        Assertions.assertEquals(3, underTest.getCount("test"));
        Assertions.assertEquals(1, underTest.getCount("another"));
    }

    @Test
    void remove() {
        CountMap<String> underTest = new CountMapImpl<>();
        underTest.add("test");
        underTest.add("test");
        underTest.add("test");
        underTest.add("another");
        underTest.add("play");
        underTest.add("doh");
        underTest.add("play");

        Assertions.assertEquals(3, underTest.remove("test"));
        Assertions.assertEquals(0, underTest.getCount("test"));

        Assertions.assertEquals(1, underTest.remove("another"));
        Assertions.assertEquals(0, underTest.remove("test"));
    }

    @Test
    void size() {
        CountMap<String> underTest = new CountMapImpl<>();
        underTest.add("test");
        underTest.add("test");
        underTest.add("test");
        underTest.add("another");
        underTest.add("play");
        underTest.add("doh");
        underTest.add("play");

        Assertions.assertEquals(4, underTest.size());
    }

    @Test
    void addAllToEmpty() {
        CountMap<String> underTest = new CountMapImpl<>();

        CountMap<String> src = new CountMapImpl<>();
        src.add("test");
        src.add("test");
        src.add("test");
        src.add("another");
        src.add("play");
        src.add("doh");
        src.add("play");

        underTest.addAll(src);
        Assertions.assertEquals(4, underTest.size());
        Assertions.assertEquals(3, underTest.getCount("test"));
        Assertions.assertEquals(1, underTest.getCount("another"));
    }

    @Test
    void addAllToNonEmpty() {
        CountMap<String> underTest = new CountMapImpl<>();
        underTest.add("play");
        underTest.add("play");
        underTest.add(("str"));

        CountMap<String> src = new CountMapImpl<>();
        src.add("test");
        src.add("test");
        src.add("test");
        src.add("another");
        src.add("play");
        src.add("doh");
        src.add("play");

        underTest.addAll(src);
        Assertions.assertEquals(5, underTest.size());
        Assertions.assertEquals(3, underTest.getCount("test"));
        Assertions.assertEquals(1, underTest.getCount("another"));
        Assertions.assertEquals(4, underTest.getCount("play"));
    }

    @Test
    void toMap() {
        CountMap<String> underTest = new CountMapImpl<>();
        underTest.add("test");
        underTest.add("test");
        underTest.add("test");
        underTest.add("another");
        underTest.add("play");
        underTest.add("doh");
        underTest.add("play");

        Map<String, Integer> actual = underTest.toMap();
        Assertions.assertEquals(4, actual.size());
        Assertions.assertEquals(3, actual.get("test"));
        Assertions.assertEquals(1, actual.get("another"));
        Assertions.assertNull(actual.get("not existing key"));


        actual.put("test", 14);
        Assertions.assertEquals(3, underTest.getCount("test"));
    }

    @Test
    void toMapWithDestination() {
        CountMap<String> underTest = new CountMapImpl<>();
        underTest.add("test");
        underTest.add("test");
        underTest.add("test");
        underTest.add("another");
        underTest.add("play");
        underTest.add("doh");
        underTest.add("play");

        Map<String, Integer> actual = new HashMap<>();
        underTest.toMap(actual);

        Assertions.assertEquals(4, actual.size());
        Assertions.assertEquals(3, actual.get("test"));
        Assertions.assertEquals(1, actual.get("another"));
        Assertions.assertNull(actual.get("not existing key"));

        actual.put("test", 14);
        Assertions.assertEquals(3, underTest.getCount("test"));
    }
}