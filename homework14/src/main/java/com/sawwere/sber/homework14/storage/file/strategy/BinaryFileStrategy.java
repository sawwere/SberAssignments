package com.sawwere.sber.homework14.storage.file.strategy;

import com.sawwere.sber.homework14.exception.SerializationException;
import com.sawwere.sber.homework14.util.SerializationUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinaryFileStrategy implements FileStorageStrategy {
    /**
     * {@inheritDoc}
     * @return '.bin'
     */
    @Override
    public String getFileExtension() {
        return ".bin";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Path path, Object obj) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(path))) {
            SerializationUtils.serialize(fileOutputStream,  obj);
        } catch (NotSerializableException e) {
            throw new SerializationException("Serialization error: attempt to cache non-serializable object", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<List<Object>, Object> tryGetCacheMap(Path path) throws IOException, ClassNotFoundException {
        Map<List<Object>, Object> cacheMap;
        try {
            cacheMap = (Map<List<Object>, Object>) SerializationUtils.deserialize(path);
        } catch (NoSuchFileException e) {
            cacheMap = new HashMap<>();
            Files.createFile(path);
        }
        return cacheMap;
    }
}
