package com.sawwere.sber.homework12.storage;

import com.sawwere.sber.homework12.Cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class InMemoryCacheStorage extends CacheStorage {
    /**
     * {метод: {список аргументов: результат}}
     */
    private final Map<Method, Map<List<Object>, Object>> memoryStorage = new ConcurrentHashMap<>();
    @Override
    public Object computeIfAbsent(Cache cacheParams,
                                  Method method,
                                  List<Object> keyArgs,
                                  Supplier<Object> callback) {
        Map<List<Object>, Object> map = memoryStorage.computeIfAbsent(method, key -> new HashMap<>());

        // block only concurrent calls for a certain method with the same key parameters
        synchronized (map) {
            if (!map.containsKey(keyArgs)) {
                var result = callback.get();
                map.put(keyArgs, result);
                return result;
            }
        }
        return map.get(keyArgs);
    }
}
