package com.sawwere.sber.homework14.service;

import com.sawwere.sber.homework14.Cache;
import com.sawwere.sber.homework14.CacheType;

import java.util.List;

public interface Service {
    @Cache(identityBy = {String.class}, cacheType = CacheType.IN_MEMORY)
    String run(Integer time, String name);

    @Cache(identityBy = {String.class}, cacheType = CacheType.DATABASE)
    String runInDatabase(Integer time, String name);

    @Cache(cacheType = CacheType.DATABASE, maxListSize = 100)
    List<Integer> getListOfSize(Integer size);
}
