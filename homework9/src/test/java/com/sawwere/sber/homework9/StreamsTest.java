package com.sawwere.sber.homework9;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class StreamsTest {

    @Test
    void withoutOperations() {
        List<Integer> arr = new ArrayList<>(List.of(new Integer[]{4, 8 , 15, 16, 23, 42}));

        Map<Integer, Integer> expected = Map.of(
                4, 4,
                8, 8,
                15, 15,
                16, 16,
                23, 23,
                42, 42
        );
        var actual = Streams.of(arr)
                .toMap(x -> x, y -> y);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void filter() {
        List<Integer> arr = List.of(new Integer[]{4, 8 , 15, 16, 23, 42});
        List<Integer> arrCopy = new ArrayList<>(arr);

        Map<Integer, Integer> expected = Map.of(
                4, 4,
                8, 8,
                16, 16,
                42, 42
        );
        var actual = Streams.of(arr)
                .filter(x -> x % 2 == 0)
                .toMap(x -> x, y -> y);
        Assertions.assertEquals(expected, actual);
        // Check that the initial collection did not change
        Assertions.assertEquals(arrCopy, arr);
    }

    @Test
    void filterWhenAllElementsNotPass() {
        List<Integer> arr = List.of(new Integer[]{4, 8 , 15, 16, 23, 42});

        Map<Integer, Integer> expected = Map.of();
        var actual = Streams.of(arr)
                .filter(x -> x < 0)
                .toMap(x -> x, y -> y);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void filterWhenAllElementsNotPassThenTransform() {
        List<Integer> arr = List.of(new Integer[]{4, 8 , 15, 16, 23, 42});

        Map<Integer, Integer> expected = Map.of();
        var actual = Streams.of(arr)
                .filter(x -> x < 0)
                .transform(x -> x * 2)
                .toMap(x -> x, y -> y);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void transform() {
        List<Integer> arr = List.of(new Integer[]{4, 8 , 15, 16, 23, 42});
        List<Integer> arrCopy = new ArrayList<>(arr);

        Map<String, Integer> expected = Map.of(
                "4", 4,
                "8", 8,
                "15", 15,
                "16", 16,
                "23", 23,
                "42", 42
        );
        var actual = Streams.of(arr)
                .transform(String::valueOf)
                .toMap(x -> x, Integer::parseInt);
        Assertions.assertEquals(expected, actual);
        // Check that the initial collection did not change
        Assertions.assertEquals(arrCopy, arr);
    }

    @Test
    void filterThenTransformCombined() {
        List<Integer> arr = List.of(new Integer[]{4, 8 , 15, 16, 23, 42});

        Map<String, Integer> expected = Map.of(
                "23", 23,
                "42", 42
        );
        var actual = Streams.of(arr)
                .filter(x -> x > 20)
                .transform(String::valueOf)
                .toMap(x -> x, Integer::parseInt);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void transformThenFilterCombined() {
        List<Integer> arr = List.of(new Integer[]{4, 8 , 15, 16, 23, 42});

        Map<Integer, Integer> expected = Map.of(
                4, 16,
                8, 32
        );
        var actual = Streams.of(arr)
                .transform(x -> x * 4)
                .filter(x -> x < 50)
                .toMap(x -> x / 4, y -> y);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void toMap() {
        List<Integer> arr = List.of(new Integer[]{4, 8 , 15, 16, 23, 42});

        Map<Integer, String> expected = Map.of(
                4, "I love number 4",
                8, "I love number 8",
                5, "I love number 15",
                6, "I love number 16",
                3, "I love number 23",
                2, "I love number 42"
        );
        var actual = Streams.of(arr)
                .toMap(x -> x % 10, y -> "I love number " + y);
        Assertions.assertEquals(expected, actual);
    }
}
