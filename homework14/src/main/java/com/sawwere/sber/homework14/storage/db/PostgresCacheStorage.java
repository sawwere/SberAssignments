package com.sawwere.sber.homework14.storage.db;

import com.sawwere.sber.homework14.util.SerializationUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Реализация хранилища кеша, которая предназначена для работы с базой данных PostgreSQL
 */
public class PostgresCacheStorage extends DBStorage {
    public PostgresCacheStorage(Connection connection) {
        super(connection);
    }

    @Override
    protected void tryInitDB() {
        try {
            var createScript = """
                    CREATE TABLE IF NOT EXISTS cached_methods (name TEXT,  key_args bytea, result bytea);
                    CREATE INDEX IF NOT EXISTS idx_cached_methods_name_key_args ON cached_methods (name, key_args);
                    
                    """;
            var statement = getConnection().createStatement();
            statement.execute(createScript);
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    @Override
    protected void put(CachedMethodEntity entity)
            throws SQLException, IOException {
        Objects.requireNonNull(entity);
        String selectStatement = """
            INSERT INTO cached_methods
                (name, key_args, result)
            VALUES
                (?, ?, ?);
        """;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(selectStatement)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBytes(2, SerializationUtils.serialize(entity.getKeyArgs()));
            preparedStatement.setBytes(3, SerializationUtils.serialize(entity.getResult()));
            preparedStatement.executeUpdate();
        }
    }

    @Override
    protected Optional<CachedMethodEntity> get(String methodName, List<Object> keyArgs)
            throws SQLException, ClassNotFoundException, IOException {
        String selectStatement = """
            SELECT result
            FROM cached_methods
            WHERE
                name = ? AND key_args = ?;
        """;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(selectStatement)) {
            preparedStatement.setString(1, methodName);
            preparedStatement.setBytes(2, SerializationUtils.serialize(keyArgs));
            var queryResult = preparedStatement.executeQuery();
            if (queryResult.next()) {
                Object deserializedResult = SerializationUtils
                        .deserialize(queryResult.getBinaryStream("result"));
                return Optional.of(
                        new CachedMethodEntity(methodName, keyArgs, deserializedResult)
                );
            }
        }

        return Optional.empty();
    }
}
