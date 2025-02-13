package com.sawwere.sber.homework8.cache.storage.file;

import com.sawwere.sber.homework8.cache.Cache;
import com.sawwere.sber.homework8.cache.exception.CacheException;
import com.sawwere.sber.homework8.cache.exception.SerializationException;
import com.sawwere.sber.homework8.cache.storage.CacheStorage;
import com.sawwere.sber.homework8.cache.storage.file.strategy.BinaryFileStrategy;
import com.sawwere.sber.homework8.cache.storage.file.strategy.FileStorageStrategy;
import com.sawwere.sber.homework8.cache.storage.file.strategy.ZipFileStrategy;
import com.sawwere.sber.homework8.cache.util.FileUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public class FileCacheStorage extends CacheStorage {
    private final Path cacheDir;

    public FileCacheStorage(Path cacheDir, Boolean shouldCleanDir) {
        this.cacheDir = cacheDir;
        try {
            if (!Files.exists(cacheDir)) {
                Files.createDirectory(cacheDir);
            } else if (shouldCleanDir) {
                FileUtils.cleanDirectory(cacheDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object computeIfAbsent(Cache cacheParams,
                                  Method method,
                                  List<Object> keyArgs,
                                  Supplier<Object> callback) {
        FileStorageStrategy strategy = cacheParams.zip() ?
                new ZipFileStrategy() :
                new BinaryFileStrategy();

        Path fileName = cacheDir.resolve(cacheParams.fileNamePrefix()
                + method.getDeclaringClass().getName()
                + method.getName()
                + strategy.getFileExtension()
        );
        try {
            Map<List<Object>, Object> map;
            if (!Files.exists(fileName)) {
                FileUtils.createEmptyFile(fileName);
                map = new HashMap<>();
            } else {
                map = strategy.tryGetCacheMap(fileName);
            }
            if (!map.containsKey(keyArgs)) {
                var x = callback.get();
                map.put(keyArgs, x);
            }
            strategy.save(fileName, map);
            return map.get(keyArgs);
        } catch (IOException e) {
            throw new CacheException("Requested cache file cannot be read", e);
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Cache file is corrupted. Unable to deserialize data", e);
        }
    }
}
