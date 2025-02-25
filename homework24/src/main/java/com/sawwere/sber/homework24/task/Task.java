package com.sawwere.sber.homework24.task;

import java.util.concurrent.Callable;

public class Task<T> {
    private TaskState state;
    private final Callable<? extends T> callable;
    private T result;
    private TaskExecutionException exception;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
        state = TaskState.INIT;
    }

    /**
     * Возвращает результат работы Callable.
     * Выполнение callable начинает тот поток, который первый вызвал метод get().
     * Если несколько потоков одновременно вызывают этот метод, то выполнение начнется только в одном потоке,
     * а остальные будут ожидать конца выполнения.
     * <p>
     * Если при вызове get() результат уже просчитан,
         * то он будет возвращен сразу без задержек на вход в синхронизированную область.
     * </p>
     * <p>
     * Если при просчете результата произошел Exception, то всем потокам при вызове get(),
     * будет выброшен кидать этот TaskExecutionException
     * </p>
     * @return результат выполнения Callable
     */
    public T get() {
        if (state == TaskState.FINISH) {
            return result;
        }
        throwIfFailed();
        synchronized (this) {
            if (state == TaskState.INIT) {
                try {
                    result = callable.call();
                    state = TaskState.FINISH;
                    return result;
                } catch (Exception e) {
                    state = TaskState.FAIL;
                    exception = new TaskExecutionException("Task failed during execution", e);
                }
            }
            throwIfFailed();
            return result;
        }
    }

    private void throwIfFailed() {
        if (state == TaskState.FAIL) {
            throw exception;
        }
    }

    enum TaskState {
        INIT,
        FINISH,
        FAIL
    }
}
