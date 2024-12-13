package com.sawwere.sber.homework8.cache.storage.file.strategy;

import com.sawwere.sber.homework8.cache.exception.CacheException;
import com.sawwere.sber.homework8.cache.exception.SerializationException;
import com.sawwere.sber.homework8.cache.util.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileStrategy implements FileStorageStrategy {
    /**
     * {@inheritDoc}
     * @return '.zip'
     */
    @Override
    public String getFileExtension() {
        return ".zip";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Path path, Object obj) {
        try (ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(String.valueOf(path)))) {
            zipStream.putNextEntry(new ZipEntry("cacheEntry"));
            FileUtils.serialize(zipStream,  obj);
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
        try (ZipFile zipFile = new ZipFile(String.valueOf(path))) {
            InputStream cacheEntryStream = zipFile.getInputStream(new ZipEntry("cacheEntry"));
            Map<List<Object>, Object> cacheMap = (Map<List<Object>, Object>) FileUtils.deserialize(cacheEntryStream);
            cacheEntryStream.close();
            return cacheMap;
        }
    }
}
