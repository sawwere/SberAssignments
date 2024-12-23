package com.sawwere.sber.homework11;

public interface ThreadPool {
    /**
     * Starts ThreadPool. Threads are inactive until there is a new job in the queue.
     */
    void start();

    /**
     * Adds task to the queue. One of waiting threads will accept and run this task.
     * Each task will be completed exactly 1 time.
     * @param task the task to execute
     */
    void execute(Runnable task);

    /**
     * Returns the current amount of threads in the pool
     * @return the current amount of threads
     */
    int getCurrentPoolSize();

    /**
     * Stops accepting new tasks from queue, but in-progress tasks will be still run.
     */
    void shutdown();

    /**
     * Stops accepting new tasks from queue, all in-progress tasks will be interrupted
     */
    void stop();
}
