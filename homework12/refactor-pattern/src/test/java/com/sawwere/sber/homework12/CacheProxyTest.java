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
import java.util.List;

public class CacheProxyTest {
    private static final Path cacheDir = Path.of("./temp");
    @AfterAll
    static void afterAll() throws IOException {
        FileUtils.cleanDirectory(cacheDir);
    }

    @Test
    void cacheInMemoryShouldReturnCachedValue() {
        Service service = CacheProxy.cache(new ServiceImpl(),
                CacheProxyConfig.builder()
                        .build()
        );

        String value1 = service.run(1, "name");
        String value2 = service.run(2, "name");
        Assertions.assertEquals(value1, value2);
    }

    @Test
    void cacheInFileShouldReturnCachedValue() {
        Service service = CacheProxy.cache(new ServiceImpl(),
                CacheProxyConfig.builder()
                        .cacheDir(cacheDir)
                        .shouldCleanOnExit(true)
                        .build()
        );

        String value1 = service.runInFile(1, "name");
        String value2 = service.runInFile(2, "name");
        Assertions.assertEquals(value1, value2);
    }

    @Test
    void cacheInZipShouldReturnCachedValue() {
        Service service = CacheProxy.cache(new ServiceImpl(),
                CacheProxyConfig.builder()
                        .cacheDir(cacheDir)
                        .shouldCleanOnExit(true)
                        .build()
        );

        String value1 = service.runInZip(1, "name");
        String value2 = service.runInZip(2, "name");
        Assertions.assertEquals(value1, value2);
    }

    @Test
    void cacheTruncatesList() {
        Service service = CacheProxy.cache(new ServiceImpl(),
                CacheProxyConfig.builder()
                        .cacheDir(cacheDir)
                        .build()
        );

        List<Integer> value1 = service.getListOfSize(1_000_000);
        Assertions.assertEquals(100, value1.size());
    }
}
