package com.sawwere.sber.homework24.exmanager;

public interface Context {
    /**
     * Возвращает количество тасков, которые на текущий момент успешно выполнились
     * @return целое неотрицательное число
     */
    int getCompletedTaskCount();

    /**
     * Возвращает количество тасков, при выполнении которых произошел Exception
     * @return целое неотрицательное число
     */
    int getFailedTaskCount();

    /**
     * Возвращает количество тасков, которые не были выполнены из-за отмены
     * @return целое неотрицательное число
     */
    int getInterruptedTaskCount();

    /**
     * Отменяет выполнения тасков, которые еще не начали выполняться.
     */
    void interrupt();

    /**
     * Возвращает <code>true</code>, если ранее был вызван метод остановки выполнения тасков, <code>false</code> в противном случае
     * @return <code>true</code>, если ранее был вызван метод остановки выполнения тасков, <code>false</code> в противном случае
     */
    boolean isInterrupted();

    /**
     * Возвращает <code>true</code>, если все таски были выполнены или отменены, <code>false</code> в противном случае
     * @return <code>true</code>, если все таски были выполнены или отменены, <code>false</code> в противном случае
     */
    boolean isFinished();
}
