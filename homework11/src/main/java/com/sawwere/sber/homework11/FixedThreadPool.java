package com.sawwere.sber.homework11;

public class FixedThreadPool extends ScalableThreadPool {
    public FixedThreadPool(int poolSize, int queueCapacity) {
        super(poolSize, poolSize, queueCapacity);
    }
}
