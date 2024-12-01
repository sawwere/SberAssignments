package com.sawwere.sber.homework5.metric;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PerformanceProxy implements InvocationHandler {
    private final Object delegate;

    public PerformanceProxy(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Metric.class)) {
            return invoke(method, args);
        }
        long start = System.nanoTime();
        Object result = invoke(method, args);
        System.out.printf("Время работы метода в нс: %d%n", System.nanoTime() - start);
        return result;
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegate, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot invoke method", e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
