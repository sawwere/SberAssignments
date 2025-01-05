package com.sawwere.sber.homework13.storage.file;

import com.sawwere.sber.homework13.Cache;
import com.sawwere.sber.homework13.exception.CacheException;
import com.sawwere.sber.homework13.exception.SerializationException;
import com.sawwere.sber.homework13.storage.CacheStorage;
import com.sawwere.sber.homework13.storage.file.strategy.BinaryFileStrategy;
import com.sawwere.sber.homework13.storage.file.strategy.FileStorageStrategy;
import com.sawwere.sber.homework13.storage.file.strategy.ZipFileStrategy;
import com.sawwere.sber.homework13.util.FileUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;


public class FileCacheStorage extends CacheStorage {
    private final Path cacheDir;
    // Block will affect the file containing method results.
    // Concurrent calls will have to wait until other thread completes read/write operation with the file
    private final Map<Path, Lock> lockMap = new ConcurrentHashMap<>();

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
        //to lock file by its name
        Lock fileLock = lockMap.computeIfAbsent(fileName, key -> new ReentrantLock());

        try {
            fileLock.lock();
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
        } finally {
            fileLock.unlock();
        }
    }
}
