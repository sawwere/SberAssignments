package com.sawwere.sber.homework14.exmanager;

import java.util.concurrent.atomic.AtomicInteger;

public class ContextImpl implements Context{
    private final int totalTaskCount;
    final AtomicInteger completedTaskCount = new AtomicInteger(0);
    final AtomicInteger failedTaskCount = new AtomicInteger(0);
    final AtomicInteger interruptedTaskCount = new AtomicInteger(0);
    private volatile boolean isInterrupted = false;


    public ContextImpl(int totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
    }

    @Override
    public int getCompletedTaskCount() {
        return completedTaskCount.get();
    }

    @Override
    public int getFailedTaskCount() {
        return failedTaskCount.get();
    }

    @Override
    public int getInterruptedTaskCount() {
        return interruptedTaskCount.get();
    }

    @Override
    public void interrupt() {
        isInterrupted = true;
    }

    @Override
    public boolean isInterrupted() {
        return isInterrupted;
    }

    @Override
    public boolean isFinished() {
        return totalTaskCount == getCompletedTaskCount() + getFailedTaskCount() + getInterruptedTaskCount();
    }
}
