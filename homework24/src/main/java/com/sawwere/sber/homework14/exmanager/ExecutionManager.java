package com.sawwere.sber.homework14.exmanager;

public interface ExecutionManager {
    /**
     * Неблокирующий метод, который сразу возвращает объект контекста {@link Context} выполнения передаваемых задач
     * @param callback колбек, который вызовется ровно 1 раз после завершения всех задач
     * @param tasks задания которые ExecutionManager должен выполнять параллельно
     * @return объект контекста {@link Context} выполнения передаваемых задач
     */
    Context execute(Runnable callback, Runnable... tasks);
}
