package com.sawwere.sber.homework24;

import com.sawwere.sber.homework24.task.Task;
import com.sawwere.sber.homework24.task.TaskExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskTest {
    @Test
    void testCallableExecutesOnce() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Callable<Integer> callable = () -> {
            counter.incrementAndGet();
            return 666;
        };

        var task = new Task<>(callable);

        var executorService = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 12; i++) {
            executorService.submit(task::get);
        }

        Thread.sleep(400);
        Assertions.assertEquals(1, counter.get());
    }

    @Test
    void testCallableThrowsException() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        Callable<Integer> callable = () -> {
            throw new RuntimeException();
        };

        var task = new Task<>(callable);
        List<Future<Integer>> futureList = new ArrayList<>();
        var executorService = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 12; i++) {
            futureList.add(executorService.submit(task::get));
        }

        Thread.sleep(400);

        for (var future : futureList) {
           Exception ex = Assertions.assertThrowsExactly(ExecutionException.class, future::get);
           Assertions.assertInstanceOf(TaskExecutionException.class, ex.getCause());
        }
        Assertions.assertEquals(0, counter.get());
    }
}
