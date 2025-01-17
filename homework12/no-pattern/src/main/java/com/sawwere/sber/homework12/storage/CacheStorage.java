package com.sawwere.sber.homework12.storage;

import com.sawwere.sber.homework12.Cache;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

public abstract class CacheStorage {
    /**
     * Returns cached value if method was called earlier with the same key arguments.
     * If it was not, executes callback function saving result into the cache storage
     * while returning that result value.
     * @param cacheParams cache annotation, containing parameters of caching
     * @param method cached method
     * @param keyArgs selected method arguments classes used as key to store cache data
     * @param callback function that will be executed if there is no cached value
     * @return result of the callback function or its cached value
     */
    public abstract Object computeIfAbsent(Cache cacheParams,
                                           Method method,
                                           List<Object> keyArgs,
                                           Supplier<Object> callback);
}
