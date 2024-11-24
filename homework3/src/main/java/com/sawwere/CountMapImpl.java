package com.sawwere;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CountMapImpl<V> implements CountMap<V> {
    private final Map<V, Integer> innerStorage = new HashMap<>();

    @Override
    public int getCount(V o) {
        return innerStorage.getOrDefault(o, 0);
    }

    @Override
    public void add(V o) {
        innerStorage.put(o, getCount(o) + 1);
    }

    @Override
    public int remove(V o) {
        Integer result = innerStorage.remove(o);
        return result == null ? 0 : result;
    }

    @Override
    public int size() {
        return innerStorage.size();
    }

    @Override
    public void addAll(CountMap<V> source) {
        for (V key : source.toMap().keySet()) {
            innerStorage.put(key, getCount(key) + source.getCount(key));
        }
    }

    @Override
    public Map<V, Integer> toMap() {
        return innerStorage.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void toMap(Map<V, Integer> destination) {
        destination.putAll(innerStorage);
    }
}
