package com.sawwere.sber.homework14.proxy;

import com.sawwere.sber.homework14.Cache;
import com.sawwere.sber.homework14.storage.CacheStorage;
import com.sawwere.sber.homework14.storage.InMemoryCacheStorage;
import com.sawwere.sber.homework14.storage.db.DBStorage;
import com.sawwere.sber.homework14.storage.file.FileCacheStorage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class CacheProxy implements InvocationHandler {
    private final InMemoryCacheStorage inMemoryCacheStorage = new InMemoryCacheStorage();
    private final FileCacheStorage fileCacheStorage;
    private final DBStorage dbStorage;
    private final Object delegate;

    private CacheProxy(Object delegate, CacheProxyConfig config) {
        this.delegate = delegate;
        fileCacheStorage = new FileCacheStorage(config.getCacheDir(), config.getShouldCleanOnStart());
        this.dbStorage = config.getDbStorage();
    }

    /**
     * Creates new proxy instance
     * @param obj target object
     * @param config configuration of the proxy
     * @return new cache proxy
     */
    public static <T> T cache(T obj, CacheProxyConfig config) {
        return (T) Proxy.newProxyInstance(CacheProxy.class.getClassLoader(),
                obj.getClass().getInterfaces(), new CacheProxy(obj, config));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Cache.class)) {
            return method.invoke(delegate, args);
        }
        var annotation = method.getAnnotation(Cache.class);
        return switch (annotation.cacheType()) {
            case FILE -> cachedInvoke(fileCacheStorage, annotation, method, args);
            case IN_MEMORY ->  cachedInvoke(inMemoryCacheStorage, annotation, method, args);
            case DATABASE -> cachedInvoke(dbStorage, annotation, method, args);
        };
    }

    private Object innerInvoke(Cache cacheParams, Method method, Object[] args) {
        try {
            Object invokeResult = method.invoke(delegate, args);
            if (invokeResult instanceof List list) {
                invokeResult = new ArrayList<String>(list.subList(0, Math.min(cacheParams.maxListSize(), list.size())));
            }
            return invokeResult;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Object cachedInvoke(CacheStorage cacheStorage, Cache cacheParams, Method method, Object[] args) {
        List<Object> argKey = getKeyArgs(args, method.getAnnotation(Cache.class).identityBy());

        return cacheStorage.computeIfAbsent(cacheParams, method, argKey, () -> innerInvoke(cacheParams, method, args));
    }

    /**
     * Filters method arguments. Leaves those of them whose type is assignable from any class from the identityBy array.
     * Those arguments can be later used as key in a caching Map.
     * @param args method arguments
     * @param identityBy array of classes used as filters
     * @return new list
     */
    private List<Object> getKeyArgs(Object[] args, Class<?>[] identityBy) {
        if (identityBy.length == 0) {
            return List.of(identityBy);
        }
        List<Object> result = new ArrayList<>();
        for (Object arg : args) {
            for (Class<?> aClass : identityBy) {
                if (aClass.isAssignableFrom(arg.getClass())) {
                    result.add(arg);
                    break;
                }
            }
        }
        return result;
    }
}
