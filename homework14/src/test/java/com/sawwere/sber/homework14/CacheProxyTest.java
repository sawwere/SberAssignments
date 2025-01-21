package com.sawwere.sber.homework14;

import com.sawwere.sber.homework14.proxy.CacheProxy;
import com.sawwere.sber.homework14.proxy.CacheProxyConfig;
import com.sawwere.sber.homework14.service.Service;
import com.sawwere.sber.homework14.service.ServiceImpl;
import com.sawwere.sber.homework14.storage.db.DBStorage;
import com.sawwere.sber.homework14.storage.db.PostgresCacheStorage;
import com.sawwere.sber.homework14.util.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CacheProxyTest {
    private static final Path cacheDir = Path.of("./temp");
    private static DBStorage dbStorage;

    private static final String URL = "jdbc:postgresql://localhost:5432/sber_homework_16";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";

    @BeforeAll
    static void beforeAll() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            dbStorage = new PostgresCacheStorage(connection);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void afterAll() throws IOException {
        FileUtils.cleanDirectory(cacheDir);
    }

    @Test
    void cacheInDatabaseShouldReturnCachedValue() {
        Service service = CacheProxy.cache(new ServiceImpl(),
                CacheProxyConfig.builder()
                        .dbStorage(dbStorage)
                        .build()
        );
        // проверяем, что результаты идентичны, так как совпадают ключевые строковые аргументы вызываемого метода
        String value1 = service.runInDatabase(100, "name");
        String value2 = service.runInDatabase(200, "name");
        Assertions.assertEquals(value1, value2);
    }

    @Test
    void cacheTruncatesList() {
        Service service = CacheProxy.cache(new ServiceImpl(),
                CacheProxyConfig.builder()
                        .dbStorage(dbStorage)
                        .build()
        );

        var value1 = service.getListOfSize(10000);
        var value2 = service.getListOfSize(2028);
        Assertions.assertEquals(100, value1.size());
        Assertions.assertEquals(value1, value2);
    }
}
