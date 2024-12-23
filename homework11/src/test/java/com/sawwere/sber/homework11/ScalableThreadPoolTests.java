package com.sawwere.sber.homework11;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ScalableThreadPoolTests {

    @Test
    void workerCountIncreaseWhenManyTasks() {
        var minSize = 1;
        var maxSize = 3;
        ScalableThreadPool threadPool = new ScalableThreadPool(minSize, maxSize, 10_000);
        threadPool.start();
        Assertions.assertEquals(1, threadPool.getCurrentPoolSize());

        for (int i = 0; i < 9; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                try {
                    System.out.println("Start task " + finalI);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Assertions.assertEquals(maxSize, threadPool.getCurrentPoolSize());
        threadPool.stop();
    }

    @Test
    void workerCountDecreaseWhenTasksCompleted() {
        var minSize = 1;
        var maxSize = 3;

        ScalableThreadPool threadPool = new ScalableThreadPool(minSize, maxSize, 10_000);
        threadPool.start();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                System.out.println("Start task " + finalI);
            });
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(minSize, threadPool.getCurrentPoolSize());
        threadPool.stop();
    }

    @Test
    void throwsWhenExecuteAfterShutdown() {
        var minSize = 1;
        var maxSize = 3;

        ScalableThreadPool threadPool = new ScalableThreadPool(minSize, maxSize, 10_000);
        threadPool.start();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                try {
                    System.out.println("Start task " + finalI);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
        Assertions.assertThrows(IllegalStateException.class, () ->
                threadPool.execute( () -> System.out.println("Will not be printed"))
        );
        threadPool.stop();
    }

    @Test
    void taskExecutedOnlyOnce() {
        var minSize = 1;
        var maxSize = 3;

        // if the task is completed more than once, this list will contain duplicate numbers
        CopyOnWriteArrayList<Integer> actual = new CopyOnWriteArrayList<>();
        List<Integer> expected = IntStream.range(0, 9).boxed().collect(Collectors.toList());

        ScalableThreadPool threadPool = new ScalableThreadPool(minSize, maxSize, 10_000);
        threadPool.start();
        for (int i = 0; i < 9; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                actual.add(finalI);
            });
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // number of items must be equal to the number of executed tasks
        Assertions.assertEquals(9, actual.size());
        // items could have been added in an unpredicted order.
        // Sorting for comparison with expected values
        actual.sort(Comparator.naturalOrder());
        Assertions.assertEquals(expected, actual);
        threadPool.stop();
    }
}