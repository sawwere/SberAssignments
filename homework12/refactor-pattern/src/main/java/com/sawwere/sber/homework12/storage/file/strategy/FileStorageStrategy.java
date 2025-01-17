package com.sawwere.sber.homework12.storage.file.strategy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface FileStorageStrategy {
    /**
     * Returns the file extension corresponding to the specific save implementation
     * @return new string
     */
    String getFileExtension();

    /**
     * Serializes and saves specified object in a file
     * @param path file path
     * @param obj object to be serialized
     */
    void save(Path path, Object obj);

    /**
     * Reads the file with the specified path and returns its contents
     * @param path file path
     * @return content of the file
     */
    Map<List<Object>, Object> tryGetCacheMap(Path path) throws IOException, ClassNotFoundException;
}
