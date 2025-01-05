package com.sawwere.sber.homework13.service;

import com.sawwere.sber.homework13.Cache;
import com.sawwere.sber.homework13.CacheType;

import java.util.List;

public interface Service {
    @Cache(identityBy = {String.class}, cacheType = CacheType.IN_MEMORY)
    String run(Integer time, String name);

    @Cache(identityBy = {String.class}, cacheType = CacheType.IN_MEMORY)
    String runWithAnotherName(Integer time, String name);

    @Cache(identityBy = {String.class}, cacheType = CacheType.FILE, fileNamePrefix = "temp_")
    String runInFile(Integer time, String name);

    @Cache(identityBy = {String.class}, cacheType = CacheType.FILE, zip = true)
    String runInZip(Integer time, String name);

    @Cache(cacheType = CacheType.IN_MEMORY, maxListSize = 100)
    List<Integer> getListOfSize(Integer size);

    @Cache(identityBy = {Integer.class}, cacheType = CacheType.FILE)
    NonSerializable runNonSerializable(Integer time);
}
