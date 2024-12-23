package com.sawwere.sber.homework11;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ThreadPool in which the number of running threads can be increased from the minimum to the maximum if,
 * when adding a new task to the queue, there is no free thread to execute this task.
 * If there is no task in the queue, the number of threads will again be reduced to the min value.
 */
public class ScalableThreadPool implements ThreadPool {
    private final int minPoolSize;
    private final int maxPoolSize;
    private final ReentrantLock mainLock;
    private final Set<Worker> workers;
    private final BlockingQueue<Runnable> taskQueue;
    private volatile boolean isRunning;
    // Number of worker threads ready to accept a new task
    private final AtomicInteger waitingWorkerCounter;

    public ScalableThreadPool(int minPoolSize, int maxPoolSize,
                              int queueCapacity) {
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.taskQueue = new ArrayBlockingQueue<>(queueCapacity);
        this.workers = new HashSet<>();
        waitingWorkerCounter = new AtomicInteger(0);
        isRunning = false;
        mainLock = new ReentrantLock();
    }

    @Override
    public void start() {
        if (isRunning) {
            throw new IllegalStateException("Thread pool is already running");
        }
        isRunning = true;
        waitingWorkerCounter.set(0);
        mainLock.lock();
        for (int i = 0; i < minPoolSize; i++) {
            addWorker();
        }
        mainLock.unlock();
    }

    /**
     * Adds new worker to this thread pool
     */
    private void addWorker() {
        mainLock.lock();
        Worker worker = new Worker();
        workers.add(worker);

        worker.start();
        mainLock.unlock();
    }

    @Override
    public void execute(Runnable task) {
        if (!isRunning) {
            throw new IllegalStateException("Thread pool is not running, can't execute new task");
        }

        taskQueue.add(task);
        mainLock.lock();
        if (waitingWorkerCounter.get() == 0 && getCurrentPoolSize() < maxPoolSize) {
            addWorker();
        }
        mainLock.unlock();
    }

    @Override
    public int getCurrentPoolSize() {
        return workers.size();
    }

    /**
     * @implNote As a result, there will be no more than minPoolSize workers in the 'waiting' state
     * (actually number may equal to 0 - there is no need in having workers anyway).
     */
    @Override
    public void shutdown() {
        isRunning = false;
    }

    @Override
    public void stop() {
        mainLock.lock();
        isRunning = false;
        for (Worker w : workers) {
            w.interrupt();
        }
        workers.clear();
        mainLock.unlock();
    }

    final class Worker extends Thread {
        private boolean isKilled = false;

        @Override
        public void run() {
            while (!isKilled && isRunning) {
                /*
                 * Check if it is possible to kill this worker to reduce the number of active workers to the minPoolSize
                 * The worker can be terminated if it's removal will not result in
                 * the total number of workers becoming less than minPoolSize
                 */
                boolean canBeKilled;
                mainLock.lock();

                waitingWorkerCounter.incrementAndGet();
                canBeKilled = workers.size() - waitingWorkerCounter.get() >= minPoolSize;

                mainLock.unlock();

                /*
                 * If this worker can be killed check at last if new task has been added.
                 * if it was, there is still job to be done and worker should do it,
                 * otherwise worker exits the loop and proceeds to termination.
                */
                Runnable nextTask;
                if (canBeKilled) {
                    nextTask = taskQueue.poll();
                    if (nextTask == null) {
                        isKilled = true;
                        break;
                    }
                } else {
                    try {
                        nextTask = taskQueue.take();
                    } catch (InterruptedException e) {
                        isKilled = true;
                        break;
                    }
                }
                // Worker is no longer waiting for task
                waitingWorkerCounter.decrementAndGet();
                nextTask.run();
            }
            mainLock.lock();
            if (isKilled) {
                waitingWorkerCounter.decrementAndGet();
            }
            workers.remove(this);

            //System.out.println(Thread.currentThread().getName() + "removed " + waitingWorkerCounter);
            mainLock.unlock();
        }
    }
}
