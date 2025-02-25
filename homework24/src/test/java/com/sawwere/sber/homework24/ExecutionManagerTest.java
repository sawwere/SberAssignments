package com.sawwere.sber.homework24;

import com.sawwere.sber.homework24.exmanager.Context;
import com.sawwere.sber.homework24.exmanager.ExecutionManager;
import com.sawwere.sber.homework24.exmanager.ExecutionManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutionManagerTest {
    @Test
    public void testSuccessfulFinish() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        ExecutionManager contextExecutor = new ExecutionManagerImpl(executor);

        AtomicInteger callbackCounter = new AtomicInteger(0);
        Runnable callback = callbackCounter::incrementAndGet;
        Runnable task1 = () -> System.out.println("Task 1");
        Runnable task2 = () -> System.out.println("Task 2");

        Context context = contextExecutor.execute(callback, task1, task2);

        Thread.sleep(500);
        Assertions.assertTrue(context.isFinished());
        Assertions.assertEquals(1, callbackCounter.get());
        Assertions.assertEquals(0, context.getFailedTaskCount());
        Assertions.assertEquals(0, context.getInterruptedTaskCount());
        Assertions.assertEquals(2, context.getCompletedTaskCount());
    }

    @Test
    public void testWithExceptionInTask() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        ExecutionManager contextExecutor = new ExecutionManagerImpl(executor);

        AtomicInteger callbackCounter = new AtomicInteger(0);
        Runnable callback = callbackCounter::incrementAndGet;
        Runnable task1 = () -> System.out.println("Task 1");
        Runnable task2 = () -> {
            System.out.println("Task 2");
            throw new RuntimeException("error :(");
        };

        Context context = contextExecutor.execute(callback, task1, task2);

        Thread.sleep(500);
        Assertions.assertTrue(context.isFinished());
        Assertions.assertEquals(1, callbackCounter.get());
        Assertions.assertEquals(1, context.getFailedTaskCount());
        Assertions.assertEquals(0, context.getInterruptedTaskCount());
        Assertions.assertEquals(1, context.getCompletedTaskCount());
    }

    @Test
    public void testInterruptDoesNotStopRunningTasks() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        ExecutionManager contextExecutor = new ExecutionManagerImpl(executor);

        AtomicInteger callbackCounter = new AtomicInteger(0);
        Runnable callback = callbackCounter::incrementAndGet;
        Runnable task1 = () -> {
            System.out.println("Task 1");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable task2 = () -> {
            System.out.println("Task 2");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Context context = contextExecutor.execute(callback, task1, task2);

        Thread.sleep(100);
        context.interrupt();
        Thread.sleep(400);

        Assertions.assertTrue(context.isFinished());
        Assertions.assertEquals(1, callbackCounter.get());
        Assertions.assertEquals(0, context.getFailedTaskCount());
        Assertions.assertEquals(0, context.getInterruptedTaskCount());
        Assertions.assertEquals(2, context.getCompletedTaskCount());
    }

    /**
     * Проверка того, что при вызове Context::interrupt ожидающие выполнения задачи не будут выполнены
     */
    @Test
    public void testInterrupt() throws InterruptedException {
        // только 1 поток - должна выполниться 1 задача, вторая будет ждать в очереди
        // ее и будем "прерывать"
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ExecutionManager contextExecutor = new ExecutionManagerImpl(executor);

        AtomicInteger callbackCounter = new AtomicInteger(0);
        Runnable callback = callbackCounter::incrementAndGet;
        Runnable task1 = () -> {
            System.out.println("Task 1");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable task2 = () -> {
            System.out.println("Task 2");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Context context = contextExecutor.execute(callback, task1, task2);

        Thread.sleep(100);
        context.interrupt();
        Thread.sleep(400);

        Assertions.assertTrue(context.isFinished());
        Assertions.assertEquals(1, callbackCounter.get());
        Assertions.assertEquals(0, context.getFailedTaskCount());
        Assertions.assertEquals(1, context.getInterruptedTaskCount());
        Assertions.assertEquals(1, context.getCompletedTaskCount());
    }
}
