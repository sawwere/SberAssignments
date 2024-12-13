package com.sawwere.sber.homework8.cache.storage;

import com.sawwere.sber.homework8.cache.Cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class InMemoryCacheStorage extends CacheStorage {
    /**
     * {метод: {список аргументов: результат}}
     */
    private final Map<Method, Map<List<Object>, Object>> memoryStorage = new HashMap<>();

    @Override
    public Object computeIfAbsent(Cache cacheParams,
                                  Method method,
                                  List<Object> keyArgs,
                                  Supplier<Object> callback) {
        memoryStorage.putIfAbsent(method, new HashMap<>());
        if (!memoryStorage.get(method).containsKey(keyArgs)) {
            var result = callback.get();
            memoryStorage.get(method).put(keyArgs, result);
            return result;
        }
        return memoryStorage.get(method).get(keyArgs);
    }
}
