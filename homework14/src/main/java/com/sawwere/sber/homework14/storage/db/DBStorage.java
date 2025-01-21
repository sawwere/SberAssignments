package com.sawwere.sber.homework14.storage.db;

import com.sawwere.sber.homework14.Cache;
import com.sawwere.sber.homework14.exception.CacheException;
import com.sawwere.sber.homework14.exception.SerializationException;
import com.sawwere.sber.homework14.storage.CacheStorage;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Абстрактный класс для кеширования методов в базе данных.
 * Конкретные реализации должны сами реализовывать операции чтения/записи и инициализации требуемых таблиц.
 */
public abstract class DBStorage extends CacheStorage {
    @Getter
    private final Connection connection;

    public DBStorage(Connection connection) {
        this.connection = connection;
        tryInitDB();
    }

    /**
     * Инициализирует базу данных, создает необходимые таблицы.
     */
    protected abstract void tryInitDB();

    /**
     * Генерирует имя метода с которым будет храниться результаты заданного метода в базе
     * @param method кешируемый метод
     * @return строка-ключ
     */
    protected String generateMethodId(Method method) {
        return method.getDeclaringClass().getName() + '#' + method.getName();
    }

    /**
     * Сохраняет в хранилище объект {@link CachedMethodEntity},
     * что равносильно созданию в базе записи с результатом кешируемого метода.
     * @param entity объект, представляющий кешируемый метод и его результат
     */
    protected abstract void put(CachedMethodEntity entity)
            throws SQLException, IOException, ClassNotFoundException;

    /**
     * Возвращает кешируемый метод по его имени и ключевым аргументам.
     *
     * @param methodId имея метода
     * @param keyArgs ключевые аргументы метода
     * @return искомая сущность или {@literal Optional#empty()}, если таковой метод отсутствует.
     */
    protected abstract Optional<CachedMethodEntity> get(String methodId, List<Object> keyArgs)
            throws SQLException, ClassNotFoundException, IOException;


    @Override
    public Object computeIfAbsent(Cache cacheParams, Method method, List<Object> keyArgs, Supplier<Object> callback) {
        var methodId = generateMethodId(method);

        try {
            Optional<CachedMethodEntity> cachedResult = get(methodId, keyArgs);
            if (cachedResult.isPresent()) {
                return cachedResult.get().getResult();
            } else {
                var result = callback.get();
                put(new CachedMethodEntity(methodId, keyArgs, result));

                return result;
            }
        } catch (SQLException | IOException e) {
            throw new CacheException("Error occurred while operating with the database", e);
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Cache is corrupted. Unable to deserialize data", e);
        }
    }
}
