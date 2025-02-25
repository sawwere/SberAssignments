package com.sawwere.sber.homework14.exmanager;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class ExecutionManagerImpl implements ExecutionManager {
    private final ExecutorService executor;

    public ExecutionManagerImpl(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        ContextImpl context = new ContextImpl(tasks.length);
        CountDownLatch latch = new CountDownLatch(tasks.length);
        // будет ждать завершения всех задач и затем вызовет колбек
        Thread thread = new Thread(() -> {
            try {
                latch.await();
                callback.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread.start();

        for (Runnable task : tasks) {
            executor.execute( () -> {
                if (context.isInterrupted()) {
                    context.interruptedTaskCount.incrementAndGet();
                } else {
                    try {
                        task.run();
                        context.completedTaskCount.incrementAndGet();
                    } catch (Exception e) {
                        context.failedTaskCount.incrementAndGet();
                    }
                }
                latch.countDown();
            });
        }
        return context;
    }
}
