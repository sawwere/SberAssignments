package com.sawwere.sber.homework12;

import com.sawwere.sber.homework12.proxy.CacheProxy;
import com.sawwere.sber.homework12.proxy.CacheProxyConfig;
import com.sawwere.sber.homework12.service.Service;
import com.sawwere.sber.homework12.service.ServiceImpl;
import com.sawwere.sber.homework12.util.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentCacheProxyTest {
    private static final Path cacheDir = Path.of("./temp-concurrent");
    @AfterAll
    static void afterAll() throws IOException {
        FileUtils.cleanDirectory(cacheDir);
    }

    @Test
    void cacheInMemoryShouldReturnCachedValue() throws ExecutionException, InterruptedException {
        Service service = CacheProxy.cache(new ServiceImpl(),
                CacheProxyConfig.builder()
                        .build()
        );
        ExecutorService executorService = Executors.newFixedThreadPool(3, r -> {
            Thread th = new Thread(r);
            th.setDaemon(false);
            return th;
        });


        Future<String> result1 = executorService.submit(() -> service.run(100, "name"));
        Future<String> result2 = executorService.submit(() -> service.runWithAnotherName(200, "name"));
        Future<String> result3 = executorService.submit(() -> service.runWithAnotherName(300, "name"));
        Thread.sleep(1000);
        Assertions.assertNotEquals(result1.get(), result2.get());
        Assertions.assertEquals(result2.get(), result3.get());
    }

    @Test
    void cacheInFileShouldReturnCachedValue() throws InterruptedException, ExecutionException {
        Service service = CacheProxy.cache(new ServiceImpl(),
                CacheProxyConfig.builder()
                        .cacheDir(cacheDir)
                        .shouldCleanOnExit(true)
                        .build()
        );
        ExecutorService executorService = Executors.newFixedThreadPool(3, r -> {
            Thread th = new Thread(r);
            th.setDaemon(false);
            return th;
        });


        Future<String> result1 = executorService.submit(() -> service.run(100, "name"));
        Future<String> result2 = executorService.submit(() -> service.runInFile(200, "name"));
        Future<String> result3 = executorService.submit(() -> service.runInFile(300, "name"));
        Thread.sleep(1000);
        Assertions.assertNotEquals(result1.get(), result2.get());
        Assertions.assertEquals(result2.get(), result3.get());
    }

    @Test
    void cacheInZipShouldReturnCachedValue() throws InterruptedException, ExecutionException {
        Service service = CacheProxy.cache(new ServiceImpl(),
                CacheProxyConfig.builder()
                        .cacheDir(cacheDir)
                        .shouldCleanOnExit(true)
                        .build()
        );
        ExecutorService executorService = Executors.newFixedThreadPool(3, r -> {
            Thread th = new Thread(r);
            th.setDaemon(false);
            return th;
        });

        Future<String> result1 = executorService.submit(() -> service.run(100, "name"));
        Future<String> result2 = executorService.submit(() -> service.runInZip(200, "name"));
        Future<String> result3 = executorService.submit(() -> service.runInZip(300, "name"));
        Thread.sleep(1000);
        Assertions.assertNotEquals(result1.get(), result2.get());
        Assertions.assertEquals(result2.get(), result3.get());
    }
}
